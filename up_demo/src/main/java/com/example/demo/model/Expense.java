package com.example.demo.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long assetId;
    private long budgetId;
    private Double amount; // UPDATED: Use Double to allow null
    private String description;
    private LocalDate date;

    public Expense() {}
    public Expense(long id, long assetId, long budgetId, Double amount, String description, LocalDate date) {
        this.id = id;
        this.assetId = assetId;
        this.budgetId = budgetId;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getAssetId() { return assetId; }
    public void setAssetId(long assetId) { this.assetId = assetId; }
    public long getBudgetId() { return budgetId; }
    public void setBudgetId(long budgetId) { this.budgetId = budgetId; }
    public Double getAmount() { return amount; } // UPDATED
    public void setAmount(Double amount) { this.amount = amount; } // UPDATED
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public int hashCode() {
        return Objects.hash(id, assetId, budgetId, amount, description, date);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Expense expense = (Expense) obj;
        return Objects.equals(id, expense.id) &&
                Objects.equals(assetId, expense.assetId) &&
                Objects.equals(budgetId, expense.budgetId) &&
                Objects.equals(amount, expense.amount) &&
                Objects.equals(description, expense.description) &&
                Objects.equals(date, expense.date);
    }
    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", assetId=" + assetId +
                ", budgetId=" + budgetId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}