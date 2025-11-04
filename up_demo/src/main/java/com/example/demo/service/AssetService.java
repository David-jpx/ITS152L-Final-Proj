package com.example.demo.service;
import com.example.demo.model.Asset;
import com.example.demo.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // ADD THIS IMPORT

@Service
public class AssetService implements IAssetService {
    @Autowired
    private AssetRepository repository;

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
}
