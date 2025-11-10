package com.example.demo.dto;

import com.example.demo.model.Expense;

public class ExpenseDTO extends Expense {
    private String department;

    public ExpenseDTO(Expense expense, String department) {
        super(expense.getId(), expense.getAssetId(), expense.getAmount(), expense.getDescription(), expense.getDate(), expense.isConfirmed());
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}