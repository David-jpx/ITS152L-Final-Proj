package com.example.demo.service;
import com.example.demo.model.Budget;
import com.example.demo.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    // NEW: Implementations
    @Override
    public Budget findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Budget updateBudget(Long id, Budget updatedBudget) {
        Optional<Budget> budgetOpt = repository.findById(id);
        if (budgetOpt.isPresent()) {
            Budget budget = budgetOpt.get();
            budget.setDepartment(updatedBudget.getDepartment());
            budget.setAmount(updatedBudget.getAmount());
            return repository.save(budget);
        }
        return null;
    }

    @Override
    public void deleteBudget(Long id) {
        repository.deleteById(id);
    }
}