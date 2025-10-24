package com.example.demo.service;

import com.example.demo.model.Expense;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExpenseService implements IExpenseService {
    @Autowired
    private ExpenseRepository repository;

    @Override
    public List<Expense> findAll() {
        return (List<Expense>) repository.findAll();
    }

    @Override
    public Expense addExpense(Expense expense) {
        // Optional: Add budget check logic here (e.g., query BudgetService to validate amount)
        return repository.save(expense);
    }
}