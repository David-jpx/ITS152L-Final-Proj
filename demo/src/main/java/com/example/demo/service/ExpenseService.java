package com.example.demo.service;

import com.example.demo.model.Expense;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
        return repository.save(expense);
    }

    @Override
    public Expense findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Expense updateExpense(Long id, Expense updatedExpense) {
        Optional<Expense> expenseOpt = repository.findById(id);
        if (expenseOpt.isPresent()) {
            Expense expense = expenseOpt.get();
            expense.setAssetId(updatedExpense.getAssetId());
            expense.setAmount(updatedExpense.getAmount());
            expense.setDescription(updatedExpense.getDescription());
            expense.setDate(updatedExpense.getDate());
            return repository.save(expense);
        }
        return null;
    }

    @Override
    public void deleteExpense(Long id) {
        repository.deleteById(id);
    }
}