package com.example.demo.controller;

import com.example.demo.model.Asset;
import com.example.demo.service.IAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AssetRestController {
    @Autowired
    private IAssetService assetService;

    @GetMapping("/api/assets")
    public List<Asset> getAllAssets(@RequestParam(required = false) String department,
                                    @RequestParam(required = false) String status) {
        if (department != null) return assetService.findByDepartment(department);
        if (status != null) return assetService.findByStatus(status);
        return assetService.findAll();
    }

    @PostMapping("/api/assets")
    public Asset addAsset(@RequestBody Asset asset) {
        return assetService.addAsset(asset);
    }

    @PutMapping("/api/assets/{id}")
    public Asset updateAsset(@PathVariable Long id, @RequestBody Asset asset) { // NEW
        return assetService.updateAsset(id, asset);
    }

    @DeleteMapping("/api/assets/{id}")
    public void deleteAsset(@PathVariable Long id) { // NEW
        assetService.deleteAsset(id);
    }

    @PutMapping("/api/assets/{id}/approve")
    public Asset approveAsset(@PathVariable Long id) {
        return assetService.approveAsset(id);
    }

    @PutMapping("/api/assets/{id}/reject")
    public Asset rejectAsset(@PathVariable Long id) {
        return assetService.rejectAsset(id);
    }
}