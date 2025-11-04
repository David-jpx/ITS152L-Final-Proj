package com.example.demo.controller;
import com.example.demo.service.IAssetService;
import com.example.demo.service.IExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {
    @Autowired
    private IAssetService assetService;
    @Autowired
    private IExpenseService expenseService;

    @GetMapping("/reports")
    public String showReports(Model model) {
        try {
            int totalAssets = assetService.findAll().size();
            double totalExpenses = expenseService.findAll().stream()
                    .filter(e -> e.getAmount() != null)
                    .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0.0)
                    .sum();
            var assetsDueForMaintenance = assetService.getAssetsDueForMaintenance();

            model.addAttribute("totalAssets", totalAssets);
            model.addAttribute("totalExpenses", totalExpenses);
            model.addAttribute("assetsDueForMaintenance", assetsDueForMaintenance != null ? assetsDueForMaintenance : java.util.Collections.emptyList());
            return "reports";
        } catch (Exception e) {
            model.addAttribute("error", "Error generating reports: " + e.getMessage());
            return "error";
        }
    }
}