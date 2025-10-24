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
}