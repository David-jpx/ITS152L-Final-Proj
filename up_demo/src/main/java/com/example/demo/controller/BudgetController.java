package com.example.demo.controller;
import com.example.demo.model.Budget;
import com.example.demo.service.IBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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