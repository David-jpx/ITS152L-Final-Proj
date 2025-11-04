package com.example.demo.controller;
import com.example.demo.model.Expense;
import com.example.demo.service.IBudgetService;
import com.example.demo.service.IExpenseService;
import com.example.demo.service.IAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ExpenseController {
    @Autowired
    private IExpenseService expenseService;
    @Autowired
    private IBudgetService budgetService;
    @Autowired
    private IAssetService assetService;

    @GetMapping("/expenses")
    public String findExpenses(Model model) {
        List<Expense> expenses = expenseService.findAll();
        model.addAttribute("expenses", expenses);
        return "showExpenses";
    }

    @GetMapping("/add-expense")
    public String addExpense(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("budgets", budgetService.findAll());
        model.addAttribute("assets", assetService.findAll());
        return "addExpense";
    }

    @PostMapping("/add-expense")
    public String addExpenseSubmit(@ModelAttribute Expense expense, Model model) {
        if (assetService.findById(expense.getAssetId()) == null) {
            model.addAttribute("error", "Invalid Asset ID: Asset does not exist.");
            model.addAttribute("budgets", budgetService.findAll());
            model.addAttribute("assets", assetService.findAll());
            return "addExpense";
        }
        try {
            expenseService.addExpense(expense);
            return "redirect:/expenses";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("budgets", budgetService.findAll());
            model.addAttribute("assets", assetService.findAll());
            return "addExpense";
        }
    }

    @GetMapping("/edit-expense/{id}")
    public String editExpense(@PathVariable Long id, Model model) {
        System.out.println("Edit Expense - Received ID: " + id + " (Type: " + (id != null ? id.getClass() : "null") + ")");
        if (id == null || id <= 0) {
            model.addAttribute("error", "Invalid expense ID: " + id);
            return "error";
        }
        Expense expense = expenseService.findById(id);
        if (expense == null) {
            model.addAttribute("error", "Expense not found for ID: " + id);
            return "error";
        }
        model.addAttribute("expense", expense);
        model.addAttribute("budgets", budgetService.findAll());
        model.addAttribute("assets", assetService.findAll());
        return "editExpense";
    }

    @PostMapping("/edit-expense/{id}")
    public String editExpenseSubmit(@PathVariable Long id, @ModelAttribute Expense expense, Model model) {
        try {
            expenseService.updateExpense(id, expense);
            return "redirect:/expenses";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("budgets", budgetService.findAll());
            model.addAttribute("assets", assetService.findAll());
            return "editExpense";
        }
    }

    @GetMapping("/delete-expense/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses";
    }
}