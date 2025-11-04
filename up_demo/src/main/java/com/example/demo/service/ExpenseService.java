package com.example.demo.service;
import com.example.demo.model.Expense;
import com.example.demo.model.Budget;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService implements IExpenseService {
    @Autowired
    private ExpenseRepository repository;
    @Autowired
    private IBudgetService budgetService;

    @Override
    public List<Expense> findAll() {
        return (List<Expense>) repository.findAll();
    }

    @Override
    public Expense addExpense(Expense expense) {
        Budget budget = budgetService.findById(expense.getBudgetId());
        double totalExpenses = calculateTotalExpensesForBudget(expense.getBudgetId());
        Double newAmount = expense.getAmount() != null ? expense.getAmount() : 0.0;
        if (budget != null && (totalExpenses + newAmount) <= budget.getAmount()) {
            return repository.save(expense);
        } else {
            throw new RuntimeException("Expense exceeds budget limit");
        }
    }

    private double calculateTotalExpensesForBudget(Long budgetId) {
        return repository.findByBudgetId(budgetId).stream()
                .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0.0)
                .sum();
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
            expense.setBudgetId(updatedExpense.getBudgetId());
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