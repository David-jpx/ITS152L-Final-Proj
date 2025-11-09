package com.example.demo.controller;

import com.example.demo.model.Budget;
import com.example.demo.model.Expense;
import com.example.demo.service.IBudgetService;
import com.example.demo.service.IExpenseService;
import com.example.demo.service.IAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BudgetController {
    @Autowired
    private IBudgetService budgetService;

    @Autowired
    private IExpenseService expenseService;

    @Autowired
    private IAssetService assetService;

    @GetMapping("/budgets")
    public String findBudgets(Model model) {
        List<Budget> budgets = budgetService.findAll();

        // Fetch all expenses
        List<Expense> expenses = expenseService.findAll();

        // Calculate confirmed and unconfirmed totals by department (department inferred from asset)
        Map<String, Double> departmentExpenses = new HashMap<>();
        Map<String, Double> unconfirmedDepartmentTotals = new HashMap<>();

        for (Expense expense : expenses) {
            String department = "Unknown";
            try {
                var asset = assetService.findById(expense.getAssetId());
                if (asset != null && asset.getDepartment() != null) {
                    department = asset.getDepartment();
                }
            } catch (Exception ignored) {}

            double amount = expense.getAmount();
            if (expense.isConfirmed()) {
                departmentExpenses.put(department, departmentExpenses.getOrDefault(department, 0.0) + amount);
            } else {
                unconfirmedDepartmentTotals.put(department, unconfirmedDepartmentTotals.getOrDefault(department, 0.0) + amount);
            }
        }

        model.addAttribute("budgets", budgets);
        model.addAttribute("departmentExpenses", departmentExpenses);
        model.addAttribute("unconfirmedDepartmentTotals", unconfirmedDepartmentTotals);
        return "showBudgets";
    }

    @GetMapping("/add-budget")
    public String addBudget(Model model) {
        model.addAttribute("budget", new Budget());
        return "addBudget";
    }

    @PostMapping("/add-budget")
    public String addBudgetSubmit(@ModelAttribute Budget budget, Model model) {
        budgetService.addBudget(budget);
        List<Budget> budgets = budgetService.findAll();
        model.addAttribute("budgets", budgets);
        return "showBudgets";
    }

    @GetMapping("/edit-budget/{id}")
    public String editBudget(@PathVariable Long id, Model model) {
        System.out.println("Edit Budget - Received ID: " + id + " (Type: " + (id != null ? id.getClass() : "null") + ")");
        if (id == null || id <= 0) {
            model.addAttribute("error", "Invalid budget ID: " + id);
            return "error";
        }
        Budget budget = budgetService.findById(id);
        if (budget == null) {
            model.addAttribute("error", "Budget not found for ID: " + id);
            return "error";
        }
        model.addAttribute("budget", budget);
        return "editBudget";
    }

    @PostMapping("/edit-budget/{id}")
    public String editBudgetSubmit(@PathVariable Long id, @ModelAttribute Budget budget, Model model) {
        budgetService.updateBudget(id, budget);
        return "redirect:/budgets";
    }

    @GetMapping("/delete-budget/{id}")
    public String deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return "redirect:/budgets";
    }
}