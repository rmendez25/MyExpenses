package com.expenses.controller;

import com.expenses.entity.Expense;
import com.expenses.enums.ExpenseCategory;
import com.expenses.exception.ExpenseNotFoundException;
import com.expenses.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:3001")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Create a new expense
    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    // Retrieve all expenses
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // Retrieve an expense by ID
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Optional<Expense> expense = expenseService.getExpenseById(id);
        return expense.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update an existing expense
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        try {
            Expense expense = expenseService.updateExpense(id, updatedExpense);
            return new ResponseEntity<>(expense, HttpStatus.OK);
        } catch (ExpenseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an expense by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        try {
            expenseService.deleteExpense(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ExpenseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Get Expenses By Category
    @GetMapping("/byCategory")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@RequestParam ExpenseCategory category) {
        List<Expense> expenses = expenseService.getExpensesByCategory(category);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    //Get Expenses Total amount
    @GetMapping("/totalExpensesAmount")
    public ResponseEntity<Map<String, Double>> calculateTotalExpenseAmount() {
        Double total = expenseService.calculateTotalExpenseAmount();
        Map<String, Double> map = new HashMap<>();
        map.put("total", total);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //Get Expenses by Date Ranges
    @GetMapping("/betweenDates")
    public List<Expense> getExpensesBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return expenseService.getExpensesBetweenDates(startDate, endDate);
    }

    @GetMapping("/totalAmountByCategory")
    public ResponseEntity<Map<String, Double>> calculateTotalExpenseAmountByCategory(@RequestParam ExpenseCategory category) {
        Double total = expenseService.calculateTotalExpenseAmountByCategory(category);
        Map<String, Double> map = new HashMap<>();
        map.put(category.name().toLowerCase(), total);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/expensesBetweenRanges")
    public ResponseEntity<List<Expense>> getExpensesBetweenRanges(@RequestParam Double start, @RequestParam Double end) {
        List<Expense> expense = expenseService.getExpensesBetweenRanges(start, end);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }
}
