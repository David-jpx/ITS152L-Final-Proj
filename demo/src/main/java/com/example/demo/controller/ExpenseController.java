package com.example.demo.controller;

import com.example.demo.model.Expense;
import com.example.demo.service.IExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class ExpenseController {
    @Autowired
    private IExpenseService expenseService;

    @GetMapping("/expenses")
    public String findExpenses(Model model) {
        List<Expense> expenses = expenseService.findAll();
        model.addAttribute("expenses", expenses);
        return "showExpenses";
    }

    @GetMapping("/add-expense")
    public String addExpense(Model model) {
        model.addAttribute("expense", new Expense());
        return "addExpense";
    }

    @PostMapping("/add-expense")
    public String addExpenseSubmit(@ModelAttribute Expense expense, Model model) {
        expenseService.addExpense(expense);
        List<Expense> expenses = expenseService.findAll();
        model.addAttribute("expenses", expenses);
        return "showExpenses";
    }
}