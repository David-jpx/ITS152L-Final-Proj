package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

// for expense reports?

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long assetId;  // Foreign key to Asset.id
    private double amount;
    private String description;
    private LocalDate date;
    private boolean confirmed; // Whether the repair has been confirmed

    public Expense() {}

    public Expense(long id, long assetId, double amount, String description, LocalDate date, boolean confirmed) {
        this.id = id;
        this.assetId = assetId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.confirmed = confirmed;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getAssetId() { return assetId; }
    public void setAssetId(long assetId) { this.assetId = assetId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }

    @Override
    public int hashCode() {
        return Objects.hash(id, assetId, amount, description, date, confirmed);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Expense expense = (Expense) obj;
        return Objects.equals(id, expense.id) &&
               Objects.equals(assetId, expense.assetId) &&
               Double.compare(amount, expense.amount) == 0 &&
               Objects.equals(description, expense.description) &&
               Objects.equals(date, expense.date) &&
               Objects.equals(confirmed, expense.confirmed);
    }

    @Override
    public String toString() {
        return "Expense{" +
               "id=" + id +
               ", assetId=" + assetId +
               ", amount=" + amount +
               ", description='" + description + '\'' +
               ", date=" + date +
               ", confirmed=" + confirmed +
               '}';
    }
}