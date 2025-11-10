package com.example.demo.service;

import com.example.demo.service.IExpenseService;
import com.example.demo.model.Asset;
import com.example.demo.model.Expense;
import com.example.demo.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors; // ADD THIS IMPORT

@Service
public class AssetService implements IAssetService {
    @Autowired
    private AssetRepository repository;

    @Autowired
    private IExpenseService expenseService;
    
    // Repair cost mapping based on SAMPLE_DATA.md
    private static final Map<String, Double> REPAIR_COSTS = new HashMap<>();
    
    static {
        // Laptops
        REPAIR_COSTS.put("Dell XPS 15", 350.0);
        REPAIR_COSTS.put("MacBook Pro 16\"", 200.0);
        REPAIR_COSTS.put("Lenovo ThinkPad X1", 150.0);
        
        // Routers
        REPAIR_COSTS.put("Cisco ISR 4321", 75.0);
        REPAIR_COSTS.put("Juniper SRX240", 300.0);
        
        // Switches
        REPAIR_COSTS.put("Cisco Catalyst 2960", 180.0);
        REPAIR_COSTS.put("HP ProCurve 2520", 120.0);
        
        // Cables
        REPAIR_COSTS.put("Fiber Optic Cable - 50m", 85.0);
        REPAIR_COSTS.put("Cat6 Ethernet Cable - 25m", 45.0);
    }
    
    private double getRepairCost(String deviceName) {
        return REPAIR_COSTS.getOrDefault(deviceName, 100.0); // Default to 100.0 if not found
    }

    @Override
    public List<Asset> findAll() {
        return (List<Asset>) repository.findAll();
    }

    @Override
    public Asset addAsset(Asset asset) {
        return repository.save(asset);
    }

    @Override
    public Asset approveAsset(Long id) {
        Optional<Asset> assetOpt = repository.findById(id);
        if (assetOpt.isPresent()) {
            Asset asset = assetOpt.get();
            asset.setStatus("approved");
            return repository.save(asset);
        }
        return null;
    }

    @Override
    public Asset rejectAsset(Long id) {
        Optional<Asset> assetOpt = repository.findById(id);
        if (assetOpt.isPresent()) {
            Asset asset = assetOpt.get();
            asset.setStatus("rejected");
            return repository.save(asset);
        }
        return null;
    }

    // NEW: Implementations
    @Override
    public Asset findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Asset updateAsset(Long id, Asset updatedAsset) {
        Optional<Asset> assetOpt = repository.findById(id);
        if (assetOpt.isPresent()) {
            Asset asset = assetOpt.get();
            asset.setDeviceName(updatedAsset.getDeviceName());
            asset.setMacAddress(updatedAsset.getMacAddress());
            asset.setDepartment(updatedAsset.getDepartment());
            asset.setStatus(updatedAsset.getStatus());
            asset.setDateLastMaintained(updatedAsset.getDateLastMaintained());
            return repository.save(asset);
        }
        return null;
    }

    @Override
    public void deleteAsset(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Asset> findByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    @Override
    public List<Asset> findByStatus(String status) {
        return repository.findByStatus(status);
    }

    @Override
    public List<Asset> getAssetsDueForMaintenance() {
        List<Asset> allAssets = (List<Asset>) repository.findAll(); // FIX: Cast to List first
        return allAssets.stream()
                .filter(a -> a.getDateLastMaintained() != null && a.getDateLastMaintained().isBefore(LocalDate.now().minusMonths(6)))
                .collect(Collectors.toList());
    }

    @Override
    public Asset markForRepair(Long id) {
        Optional<Asset> assetOpt = repository.findById(id);
        if (assetOpt.isPresent()) {
            Asset asset = assetOpt.get();
            asset.setStatus("Marked for repair");
            Asset saved = repository.save(asset);
            
            // Create expense for the repair with actual cost from SAMPLE_DATA
            double repairCost = getRepairCost(saved.getDeviceName());
            Expense repairExpense = new Expense();
            repairExpense.setAssetId(saved.getId());
            repairExpense.setAmount(repairCost);
            repairExpense.setDescription("Repair for " + saved.getDeviceName() + " - Estimated cost: ₱" + repairCost);
            repairExpense.setDate(LocalDate.now());
            repairExpense.setConfirmed(false); // Not confirmed by default
            expenseService.addExpense(repairExpense);
            
            return saved;
        }
        return null;
    }
}
