package com.example.demo.controller;

import com.example.demo.model.Asset;
import com.example.demo.service.IAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Controller
public class AssetController {
    @Autowired
    private IAssetService assetService;

    @GetMapping("/assets")
    public String findAssets(Model model) {
        List<Asset> assets = assetService.findAll();
        model.addAttribute("assets", assets);
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
        return "showAssets";
    }
}