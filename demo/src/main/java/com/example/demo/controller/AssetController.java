package com.example.demo.controller;

import com.example.demo.model.Asset;
import com.example.demo.service.IAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
public class AssetController {
    @Autowired
    private IAssetService assetService;

    @GetMapping("/assets")
    public String findAssets(@RequestParam(required = false) String department, 
                           @RequestParam(required = false) String status, 
                           @RequestParam(required = false) String repairFilter, 
                           Model model) {
        List<Asset> assets;
        
        // Handle repair filter (show assets needing repair - both Needs repair and Marked for repair)
        if ("true".equals(repairFilter)) {
            assets = assetService.findAll().stream()
                .filter(asset -> "Needs repair".equals(asset.getStatus()) || "Marked for repair".equals(asset.getStatus()))
                .collect(java.util.stream.Collectors.toList());
        }
        // Handle department and status filtering
        else if (department != null && !department.isEmpty() && status != null && !status.isEmpty()) {
            assets = assetService.findAll().stream()
                .filter(asset -> department.equals(asset.getDepartment()) && status.equals(asset.getStatus()))
                .collect(java.util.stream.Collectors.toList());
        }
        // Handle department-only filtering
        else if (department != null && !department.isEmpty()) {
            assets = assetService.findByDepartment(department);
        }
        // Handle status-only filtering
        else if (status != null && !status.isEmpty()) {
            assets = assetService.findByStatus(status);
        }
        // No filters - show all
        else {
            assets = assetService.findAll();
        }
        
        model.addAttribute("assets", assets);
        model.addAttribute("maintenanceThreshold", LocalDate.now().minusMonths(6));
        model.addAttribute("selectedDepartment", department);
        model.addAttribute("selectedStatus", status);
        return "showAssets";
    }

    @GetMapping("/add-asset")
    public String addAsset(Model model) {
        model.addAttribute("asset", new Asset());
        return "addAsset";
    }

    @PostMapping("/add-asset")
    public String addAssetSubmit(@ModelAttribute Asset asset, Model model) {
        assetService.addAsset(asset);
        List<Asset> assets = assetService.findAll();
        model.addAttribute("assets", assets);
        model.addAttribute("maintenanceThreshold", LocalDate.now().minusMonths(6));
        return "showAssets";
    }

    @GetMapping("/edit-asset/{id}")
    public String editAsset(@PathVariable Long id, Model model) {
        System.out.println("DEBUG: Received ID for edit: '" + id + "' (Type: " + (id != null ? id.getClass() : "null") + ")");
        if (id == null || id <= 0) {
            model.addAttribute("error", "Invalid asset ID: " + id);
            return "error";
        }
        Asset asset = assetService.findById(id);
        if (asset == null) {
            model.addAttribute("error", "Asset not found for ID: " + id);
            return "error";
        }
        model.addAttribute("asset", asset);
        return "editAsset";
    }

    @PostMapping("/edit-asset/{id}")
    public String editAssetSubmit(@PathVariable Long id, @ModelAttribute Asset asset, Model model) {
        assetService.updateAsset(id, asset);
        return "redirect:/assets";
    }

    @GetMapping("/delete-asset/{id}")
    public String deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return "redirect:/assets";
    }

    @GetMapping("/mark-for-repair/{id}")
    public String markForRepair(@PathVariable Long id) {
        assetService.markForRepair(id);
        return "redirect:/assets";
    }
}