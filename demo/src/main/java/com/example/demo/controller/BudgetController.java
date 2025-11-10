package com.example.demo.controller;

import com.example.demo.model.Budget;
import com.example.demo.model.Expense;
import com.example.demo.BudgetLog;
import com.example.demo.BudgetLogRepository;
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

    @Autowired
    private BudgetLogRepository budgetLogRepository;

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

        // Fetch and add budget logs
        model.addAttribute("budgetLogs", budgetLogRepository.findAll());

        return "showBudgets";
    }

    @PostMapping("/update-budget/{id}")
    public String updateBudget(@PathVariable Long id, @RequestParam double amount, @RequestParam String action) {
        Budget budget = budgetService.findById(id);
        if (budget != null) {
            BudgetLog log = new BudgetLog();
            log.setDepartment(budget.getDepartment());

            if ("add".equals(action)) {
                budget.setAmount(budget.getAmount() + amount);
                log.setLog(amount);
                log.setDescription("Manually added");
            } else if ("deduct".equals(action)) {
                budget.setAmount(budget.getAmount() - amount);
                log.setLog(-amount);
                log.setDescription("Manually deducted");
            }
            
            budgetLogRepository.save(log);
            budgetService.updateBudget(id, budget);
        }
        return "redirect:/budgets";
    }
}