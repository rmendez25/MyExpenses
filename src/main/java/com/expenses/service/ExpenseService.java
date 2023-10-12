package com.expenses.service;

import com.expenses.entity.Expense;
import com.expenses.enums.ExpenseCategory;
import com.expenses.exception.ExpenseNotFoundException;
import com.expenses.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Create a new expense
    public Expense createExpense(Expense expense) {
        // Set created_at to the current timestamp
        expense.setCreatedAt(java.time.LocalDate.now());

        return expenseRepository.save(expense);
    }

    // Retrieve all expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // Retrieve an expense by ID
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    // Update an existing expense
    public Expense updateExpense(Long id, Expense updatedExpense) {
        if (expenseRepository.existsById(id)) {
            updatedExpense.setId(id);

            // Set updated_at to the current timestamp
            updatedExpense.setUpdatedAt(java.time.LocalDate.now());

            return expenseRepository.save(updatedExpense);
        }
        throw new ExpenseNotFoundException("Expense not found with id: " + id);
    }

    // Delete an expense by ID
    public void deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
        } else {
            throw new ExpenseNotFoundException("Expense not found with id: " + id);
        }
    }

    // Get Expenses by category
    public List<Expense> getExpensesByCategory(ExpenseCategory category) {
        if (expenseRepository.findByCategory(category).isEmpty()) {
            throw new ExpenseNotFoundException("Expense with category " + category.name() + " does not have any expense");
        }

        return expenseRepository.findByCategory(category);
    }

    // Get Expenses
    public Double calculateTotalExpenseAmount() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    //Get Expenses between date ranges
    public List<Expense> getExpensesBetweenDates(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByCreatedAtBetween(startDate, endDate);
    }

    // Calculate Expenses amount by category
    public Double calculateTotalExpenseAmountByCategory(ExpenseCategory category) {
        List<Expense> expenses = expenseRepository.findByCategory(category);

        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    // Get expenses between amount ranges
    public List<Expense> getExpensesBetweenRanges(Double start, Double end) {
       return expenseRepository.findByAmountBetween(start, end);
    }
}

