package com.example.demo.controller;

import com.example.demo.model.Budget;
import com.example.demo.service.IBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class BudgetController {
    @Autowired
    private IBudgetService budgetService;

    @GetMapping("/budgets")
    public String findBudgets(Model model) {
        List<Budget> budgets = budgetService.findAll();
        model.addAttribute("budgets", budgets);
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
}