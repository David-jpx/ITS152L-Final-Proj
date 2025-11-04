package com.example.demo.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String department;
    private double amount;

    public Budget() {}

    public Budget(long id, String department, double amount) {
        this.id = id;
        this.department = department;
        this.amount = amount;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    @Override
    public int hashCode() {
        return Objects.hash(id, department, amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Budget budget = (Budget) obj;
        return Objects.equals(id, budget.id) &&
               Objects.equals(department, budget.department) &&
               Double.compare(amount, budget.amount) == 0;
    }

    @Override
    public String toString() {
        return "Budget{" +
               "id=" + id +
               ", department='" + department + '\'' +
               ", amount=" + amount +
               '}';
    }
}