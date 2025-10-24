package com.example.demo.service;

import com.example.demo.model.Expense;
import java.util.List;

public interface IExpenseService {
    List<Expense> findAll();
    Expense addExpense(Expense expense);
}