package com.example.demo.service;

import com.example.demo.model.Asset;
import com.example.demo.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
        return null;  // Handle not found
    }

    @Override
    public Asset rejectAsset(Long id) {
        Optional<Asset> assetOpt = repository.findById(id);
        if (assetOpt.isPresent()) {
            Asset asset = assetOpt.get();
            asset.setStatus("rejected");
            return repository.save(asset);
        }
        return null;  // Handle not found
    }
}