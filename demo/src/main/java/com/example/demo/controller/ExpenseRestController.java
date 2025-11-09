package com.example.demo.controller;

import com.example.demo.model.Expense;
import com.example.demo.service.IExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ExpenseRestController {
    @Autowired
    private IExpenseService expenseService;

    @GetMapping("/api/expenses")
    public List<Expense> getAllExpenses() {
        return expenseService.findAll();
    }

    @PostMapping("/api/expenses")
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    // NEW: Edit and Delete endpoints
    @PutMapping("/api/expenses/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/api/expenses/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}