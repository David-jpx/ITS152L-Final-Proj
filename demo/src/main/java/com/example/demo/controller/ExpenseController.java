package com.example.demo.controller;

import com.example.demo.dto.ExpenseDTO;
import com.example.demo.model.Expense;
import com.example.demo.model.Budget;
import com.example.demo.model.Asset;
import com.example.demo.BudgetLog;
import com.example.demo.BudgetLogRepository;
import com.example.demo.service.IBudgetService;
import com.example.demo.service.IExpenseService;
import com.example.demo.service.IAssetService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ExpenseController {
    @Autowired
    private IExpenseService expenseService;
    @Autowired
    private IBudgetService budgetService;
    @Autowired
    private IAssetService assetService;
    @Autowired
    private BudgetLogRepository budgetLogRepository;

    @GetMapping("/expenses")
    public String findExpenses(Model model) {
        List<Expense> allExpenses = expenseService.findAll();
        
        // Filter to show only confirmed expenses (marked for repair) by default
        List<Expense> expenses = allExpenses.stream()
            .filter(Expense::isConfirmed)
            .collect(Collectors.toList());
        
        // Also include unconfirmed expenses that should be visible for confirmation
        // This allows users to still see and confirm pending expenses
        List<Expense> unconfirmedExpenses = allExpenses.stream()
            .filter(expense -> !expense.isConfirmed())
            .collect(Collectors.toList());
        
        List<Budget> budgets = budgetService.findAll();

        List<ExpenseDTO> expenseDTOs = expenses.stream()
            .map(expense -> {
                Asset asset = assetService.findById(expense.getAssetId());
                String department = (asset != null) ? asset.getDepartment() : "Unknown";
                return new ExpenseDTO(expense, department);
            })
            .collect(Collectors.toList());
            
        // Create DTOs for all expenses (both confirmed and unconfirmed)
        List<ExpenseDTO> allExpenseDTOs = allExpenses.stream()
            .map(expense -> {
                Asset asset = assetService.findById(expense.getAssetId());
                String department = (asset != null) ? asset.getDepartment() : "Unknown";
                return new ExpenseDTO(expense, department);
            })
            .collect(Collectors.toList());

        
        // Calculate confirmed expenses by department
        Map<String, Double> departmentExpenses = expenses.stream()
            .collect(Collectors.groupingBy(
                expense -> {
                    Asset asset = assetService.findById(expense.getAssetId());
                    return asset != null ? asset.getDepartment() : "Unknown";
                },
                Collectors.summingDouble(Expense::getAmount)
            ));
        
        // Calculate total expenses (confirmed only, as shown in main view)
        double confirmedTotalExpenses = expenseDTOs.stream().mapToDouble(ExpenseDTO::getAmount).sum();
        
        // Calculate unconfirmed expenses by department
        Map<String, Double> unconfirmedExpensesByDept = unconfirmedExpenses.stream()
            .collect(Collectors.groupingBy(
                expense -> {
                    Asset asset = assetService.findById(expense.getAssetId());
                    return asset != null ? asset.getDepartment() : "Unknown";
                },
                Collectors.summingDouble(Expense::getAmount)
            ));
        
        // Create budget comparison data for each department
        Map<String, Map<String, Double>> budgetComparison = budgets.stream()
            .collect(Collectors.toMap(
                Budget::getDepartment,
                budget -> {
                    Map<String, Double> deptData = new java.util.HashMap<>();
                    double currentBudget = budget.getAmount();
                    double confirmedTotal = departmentExpenses.getOrDefault(budget.getDepartment(), 0.0);
                    double unconfirmedTotal = unconfirmedExpensesByDept.getOrDefault(budget.getDepartment(), 0.0);
                    double totalExpenses = confirmedTotal + unconfirmedTotal;
                    double remainingAfterConfirmed = currentBudget - confirmedTotal;
                    double remainingAfterUnconfirmed = currentBudget - totalExpenses;
                    
                    deptData.put("currentBudget", currentBudget);
                    deptData.put("confirmedExpenses", confirmedTotal);
                    deptData.put("unconfirmedExpenses", unconfirmedTotal);
                    deptData.put("totalExpenses", totalExpenses);
                    deptData.put("remainingAfterConfirmed", remainingAfterConfirmed);
                    deptData.put("remainingAfterUnconfirmed", remainingAfterUnconfirmed);
                    
                    return deptData;
                }
            ));
        
        model.addAttribute("expenses", expenseDTOs);
        model.addAttribute("allExpenses", allExpenseDTOs); // For unconfirmed section
        model.addAttribute("budgets", budgets);
        model.addAttribute("departmentExpenses", departmentExpenses);
        model.addAttribute("totalExpenses", confirmedTotalExpenses);
        model.addAttribute("budgetComparison", budgetComparison);
        return "showExpenses";
    }

    @GetMapping("/budget-comparison")
    public String budgetComparison(Model model) {
        List<Budget> budgets = budgetService.findAll();
        List<Expense> confirmedExpenses = expenseService.findAll().stream()
            .filter(Expense::isConfirmed)
            .collect(Collectors.toList());
        
        // Calculate total expenses by department
        Map<String, Double> departmentExpenses = confirmedExpenses.stream()
            .collect(Collectors.groupingBy(
                expense -> {
                    // Get department from asset
                    Asset asset = assetService.findById(expense.getAssetId());
                    return asset != null ? asset.getDepartment() : "Unknown";
                },
                Collectors.summingDouble(Expense::getAmount)
            ));
        
        model.addAttribute("budgets", budgets);
        model.addAttribute("departmentExpenses", departmentExpenses);
        return "budgetComparison";
    }



    @GetMapping("/edit-expense/{id}")
    public String editExpense(@PathVariable Long id, Model model) {
        System.out.println("Edit Expense - Received ID: " + id + " (Type: " + (id != null ? id.getClass() : "null") + ")");
        if (id == null || id <= 0) {
            model.addAttribute("error", "Invalid expense ID: " + id);
            return "error";
        }
        Expense expense = expenseService.findById(id);
        if (expense == null) {
            model.addAttribute("error", "Expense not found for ID: " + id);
            return "error";
        }
        
        // Get the asset to determine its current status
        Asset asset = assetService.findById(expense.getAssetId());
        String currentAssetStatus = (asset != null) ? asset.getStatus() : "active";
        
        model.addAttribute("expense", expense);
        model.addAttribute("budgets", budgetService.findAll());
        model.addAttribute("assets", assetService.findAll());
        model.addAttribute("currentAssetStatus", currentAssetStatus);
        return "editExpense";
    }

    @PostMapping("/edit-expense/{id}")
    public String editExpenseSubmit(@PathVariable Long id, @ModelAttribute Expense expense, 
                                  @RequestParam(required = false) String assetStatus, Model model) {
        try {
            // Update the expense
            expenseService.updateExpense(id, expense);
            
            // Update asset status if provided
            if (assetStatus != null) {
                Asset asset = assetService.findById(expense.getAssetId());
                if (asset != null) {
                    asset.setStatus(assetStatus);
                    assetService.updateAsset(asset.getId(), asset);
                }
            }
            
            // If expense is not confirmed (not marked for repair), it should disappear from the main view
            // This is handled by the filtering logic in the findExpenses method
            
            return "redirect:/expenses";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("budgets", budgetService.findAll());
            model.addAttribute("assets", assetService.findAll());
            return "editExpense";
        }
    }

    @PostMapping("/confirm-expense/{id}")
    public String confirmExpense(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Expense expense = expenseService.findById(id);
        if (expense != null && !expense.isConfirmed()) {
            Asset asset = assetService.findById(expense.getAssetId());
            if (asset != null) {
                Budget budget = budgetService.findAll().stream()
                    .filter(b -> b.getDepartment().equals(asset.getDepartment()))
                    .findFirst()
                    .orElse(null);

                if (budget != null) {
                    double remainingBudget = budget.getAmount() - expenseService.findAll().stream()
                        .filter(e -> e.isConfirmed() && asset.getDepartment().equals(assetService.findById(e.getAssetId()).getDepartment()))
                        .mapToDouble(Expense::getAmount)
                        .sum();

                    if (remainingBudget >= expense.getAmount()) {
                        expense.setConfirmed(true);
                        expenseService.updateExpense(id, expense);

                        asset.setStatus("Under repair");
                        assetService.updateAsset(asset.getId(), asset);

                        BudgetLog log = new BudgetLog();
                        log.setDepartment(asset.getDepartment());
                        log.setLog(-expense.getAmount());
                        // Asset model uses getDeviceName()
                        log.setDescription("Repair for asset: " + asset.getDeviceName());
                        budgetLogRepository.save(log);

                        redirectAttributes.addFlashAttribute("successMessage", "Expense confirmed and budget updated.");
                    } else {
                        redirectAttributes.addFlashAttribute("errorMessage", "Not enough budget to confirm expense.");
                    }
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "No budget found for this department.");
                }
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Asset not found.");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Expense not found or already confirmed.");
        }
        return "redirect:/expenses";
    }

    @PostMapping("/confirm-department-expenses")
    public String confirmDepartmentExpenses(@RequestParam String department, @RequestParam("expenseIds") List<Long> expenseIds) {
        for (Long expenseId : expenseIds) {
            Expense expense = expenseService.findById(expenseId);
            if (expense != null && !expense.isConfirmed()) {
                // Mark expense as confirmed
                expense.setConfirmed(true);
                expenseService.updateExpense(expenseId, expense);
                
                // Update asset status to "Waiting repair"
                Asset asset = assetService.findById(expense.getAssetId());
                if (asset != null) {
                    asset.setStatus("Waiting repair");
                    assetService.updateAsset(asset.getId(), asset);
                }
            }
        }
        return "redirect:/expenses";
    }

    @PostMapping("/confirm-selected-expenses")
    public String confirmSelectedExpenses(@RequestParam(value = "expenseIds", required = false) List<Long> expenseIds, RedirectAttributes redirectAttributes) {
        if (expenseIds == null || expenseIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No expenses selected to confirm.");
            return "redirect:/expenses";
        }
        for (Long expenseId : expenseIds) {
            Expense expense = expenseService.findById(expenseId);
            if (expense != null && !expense.isConfirmed()) {
                // Mark expense as confirmed
                expense.setConfirmed(true);
                expenseService.updateExpense(expenseId, expense);
                
                // Update asset status to "Waiting repair"
                Asset asset = assetService.findById(expense.getAssetId());
                if (asset != null) {
                    asset.setStatus("Waiting repair");
                    assetService.updateAsset(asset.getId(), asset);
                    // Add an entry to Budget History (expense should reduce budget)
                    BudgetLog log = new BudgetLog();
                    log.setDepartment(asset.getDepartment());
                    // Store as negative to indicate budget reduction
                    log.setLog(-Math.abs(expense.getAmount()));
                    // Use the expense description as requested
                    log.setDescription(expense.getDescription());
                    budgetLogRepository.save(log);
                }
            }
        }
        return "redirect:/expenses";
    }
}