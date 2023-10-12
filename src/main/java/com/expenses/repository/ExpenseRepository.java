package com.expenses.repository;

import com.expenses.entity.Expense;
import com.expenses.enums.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategory(ExpenseCategory category);

    List<Expense> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);

    List<Expense> findByAmountBetween(Double start, Double end);
}

