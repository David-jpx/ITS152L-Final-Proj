package com.example.demo.service;

import com.example.demo.model.Budget;
import java.util.List;

public interface IBudgetService {
    List<Budget> findAll();
    Budget addBudget(Budget budget);
}