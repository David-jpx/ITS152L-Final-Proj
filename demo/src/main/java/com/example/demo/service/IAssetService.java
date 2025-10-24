package com.example.demo.service;

import com.example.demo.model.Asset;
import java.util.List;

public interface IAssetService {
    List<Asset> findAll();
    Asset addAsset(Asset asset);
    Asset approveAsset(Long id);
    Asset rejectAsset(Long id);
}