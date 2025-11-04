package com.example.demo.service;
import com.example.demo.model.Asset;
import java.util.List;

public interface IAssetService {
    List<Asset> findAll();
    Asset addAsset(Asset asset);
    Asset approveAsset(Long id);
    Asset rejectAsset(Long id);
    Asset findById(Long id); // NEW
    Asset updateAsset(Long id, Asset asset); // NEW
    void deleteAsset(Long id); // NEW
    List<Asset> findByDepartment(String department); // NEW
    List<Asset> findByStatus(String status); // NEW
    List<Asset> getAssetsDueForMaintenance(); // NEW
}