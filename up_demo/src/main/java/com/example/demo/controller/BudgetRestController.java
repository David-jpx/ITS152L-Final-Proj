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

    @PostMapping("/api/budgets")  // FIXED: Corrected typo from "/a pi/budgets"
    public Budget addBudget(@RequestBody Budget budget) {
        return budgetService.addBudget(budget);
    }

    // NEW: Added for full CRUD (edit and delete)
    @PutMapping("/api/budgets/{id}")
    public Budget updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
        return budgetService.updateBudget(id, budget);
    }

    @DeleteMapping("/api/budgets/{id}")
    public void deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
    }
}