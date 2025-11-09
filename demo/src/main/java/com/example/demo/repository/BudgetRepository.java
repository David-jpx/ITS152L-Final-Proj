package com.example.demo.repository;
import com.example.demo.model.Budget;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BudgetRepository extends CrudRepository<Budget, Long> {
    Optional<Budget> findById(Long id); // NEW
}