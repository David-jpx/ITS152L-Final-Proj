package com.example.demo.controller;

import com.example.demo.model.Budget;
import com.example.demo.service.IBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BudgetRestController {
    @Autowired
    private IBudgetService budgetService;

    @GetMapping("/api/budgets")
    public List<Budget> getAllBudgets() {
        return budgetService.findAll();
    }

    @PostMapping("/api/budgets")
    public Budget addBudget(@RequestBody Budget budget) {
        return budgetService.addBudget(budget);
    }
}