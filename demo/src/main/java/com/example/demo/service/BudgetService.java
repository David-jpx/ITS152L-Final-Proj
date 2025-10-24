package com.example.demo.service;

import com.example.demo.model.Budget;
import com.example.demo.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BudgetService implements IBudgetService {
    @Autowired
    private BudgetRepository repository;

    @Override
    public List<Budget> findAll() {
        return (List<Budget>) repository.findAll();
    }

    @Override
    public Budget addBudget(Budget budget) {
        return repository.save(budget);
    }
}