package com.example.demo;

import com.example.demo.model.Asset;
import com.example.demo.model.Budget;
import com.example.demo.model.Expense;
import com.example.demo.service.IAssetService;
import com.example.demo.service.IBudgetService;
import com.example.demo.service.IExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private IAssetService assetService;

    @Autowired
    private IBudgetService budgetService;

    @Autowired
    private IExpenseService expenseService;
    
    @Autowired
    private com.example.demo.repository.AssetRepository assetRepository;
    
    @Autowired
    private com.example.demo.repository.ExpenseRepository expenseRepository;
    
    @Autowired
    private com.example.demo.repository.BudgetRepository budgetRepository;

    @Autowired
    private BudgetLogRepository budgetLogRepository;

    @Override
    public void run(String... args) throws Exception {
        // Clear existing data to prevent duplicates
        clearExistingData();
        
        // Create budgets first
        Budget itBudget = new Budget();
        itBudget.setDepartment("IT Department");
        itBudget.setAmount(50000.00);
        budgetService.addBudget(itBudget);

        Budget networkBudget = new Budget();
        networkBudget.setDepartment("Network Infrastructure");
        networkBudget.setAmount(30000.00);
        budgetService.addBudget(networkBudget);

        Budget maintenanceBudget = new Budget();
        maintenanceBudget.setDepartment("Maintenance");
        maintenanceBudget.setAmount(15000.00);
        budgetService.addBudget(maintenanceBudget);

        // Create sample assets (no expenses initially - only when marked for repair)
        createLaptopAssets();
        createRouterAssets();
        createSwitchAssets();
        createCableAssets();
        createAssetsForRepairDemo();

        // Create sample budget logs
        // Load budget logs (initial funding / top-ups) from SAMPLE_DATA.md
        loadBudgetLogsFromSampleData();
    }

    private void loadBudgetLogsFromSampleData() {
        java.io.BufferedReader reader = null;
        try {
            // Try to load from classpath first
            java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("SAMPLE_DATA.md");
            if (is != null) {
                reader = new java.io.BufferedReader(new java.io.InputStreamReader(is));
            } else {
                // Fallback to filesystem (working directory)
                java.io.File f = new java.io.File("SAMPLE_DATA.md");
                if (f.exists()) {
                    reader = new java.io.BufferedReader(new java.io.FileReader(f));
                }
            }

            if (reader == null) return;

            String line;
            boolean inTable = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Detect table separator to start parsing rows
                if (line.startsWith("|")) {
                    // skip header separator line like |---|
                    if (line.matches("\\|\s*-{3,}.*")) {
                        inTable = true;
                        continue;
                    }

                    if (inTable) {
                        // Parse a table row: | Department | Type | Amount | Description |
                        String[] cols = line.split("\\|");
                        // Expecting at least 5 parts because split includes leading/trailing empty segments
                        if (cols.length >= 5) {
                            String dept = cols[1].trim();
                            String type = cols[2].trim();
                            String amountStr = cols[3].trim().replace("₱", "").replace(",", "");
                            String desc = cols[4].trim();
                            try {
                                double amount = Double.parseDouble(amountStr);
                                BudgetLog log = new BudgetLog();
                                log.setDepartment(dept);
                                // Use positive values for funding/top-ups
                                log.setLog(amount);
                                log.setDescription(desc != null && !desc.isEmpty() ? desc : (type + " for " + dept));
                                budgetLogRepository.save(log);
                            } catch (NumberFormatException nfe) {
                                // ignore malformed amount
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (reader != null) reader.close(); } catch (Exception ignore) {}
        }
    }
    
    private void clearExistingData() {
        // Use repository methods directly to avoid circular dependency
        expenseRepository.deleteAll();
        assetRepository.deleteAll();
        budgetRepository.deleteAll();
        budgetLogRepository.deleteAll();
    }

    private void createLaptopAssets() {
        // Laptop 1 - Dell XPS
        Asset laptop1 = new Asset();
        laptop1.setDeviceName("Dell XPS 15");
        laptop1.setMacAddress("00:1B:44:11:3A:B7");
        laptop1.setDepartment("IT Department");
        laptop1.setStatus("active");
        laptop1.setPrice(1500.00);
        laptop1.setDateLastMaintained(LocalDate.now().minusMonths(3));
        assetService.addAsset(laptop1);

        // Laptop 2 - MacBook Pro (needs repair - will create expense when marked)
        Asset laptop2 = new Asset();
        laptop2.setDeviceName("MacBook Pro 16\"");
        laptop2.setMacAddress("00:1B:44:11:3A:B8");
        laptop2.setDepartment("IT Department");
        laptop2.setStatus("Needs repair");
        laptop2.setPrice(2200.00);
        laptop2.setDateLastMaintained(LocalDate.now().minusMonths(8));
        assetService.addAsset(laptop2);

        // Laptop 3 - Lenovo ThinkPad
        Asset laptop3 = new Asset();
        laptop3.setDeviceName("Lenovo ThinkPad X1");
        laptop3.setMacAddress("00:1B:44:11:3A:B9");
        laptop3.setDepartment("IT Department");
        laptop3.setStatus("active");
        laptop3.setPrice(1300.00);
        laptop3.setDateLastMaintained(LocalDate.now().minusMonths(2));
        assetService.addAsset(laptop3);
    }

    private void createRouterAssets() {
        // Router 1 - Cisco
        Asset router1 = new Asset();
        router1.setDeviceName("Cisco ISR 4321");
        router1.setMacAddress("00:1B:44:22:3A:C7");
        router1.setDepartment("Network Infrastructure");
        router1.setStatus("active");
        router1.setPrice(800.00);
        router1.setDateLastMaintained(LocalDate.now().minusMonths(4));
        assetService.addAsset(router1);

        // Router 2 - Juniper (needs repair - will create expense when marked)
        Asset router2 = new Asset();
        router2.setDeviceName("Juniper SRX240");
        router2.setMacAddress("00:1B:44:22:3A:C8");
        router2.setDepartment("Network Infrastructure");
        router2.setStatus("Needs repair");
        router2.setPrice(650.00);
        router2.setDateLastMaintained(LocalDate.now().minusMonths(10));
        assetService.addAsset(router2);
    }

    private void createSwitchAssets() {
        // Switch 1 - Cisco Catalyst
        Asset switch1 = new Asset();
        switch1.setDeviceName("Cisco Catalyst 2960");
        switch1.setMacAddress("00:1B:44:33:3A:D7");
        switch1.setDepartment("Network Infrastructure");
        switch1.setStatus("active");
        switch1.setPrice(500.00);
        switch1.setDateLastMaintained(LocalDate.now().minusMonths(5));
        assetService.addAsset(switch1);

        // Switch 2 - HP ProCurve
        Asset switch2 = new Asset();
        switch2.setDeviceName("HP ProCurve 2520");
        switch2.setMacAddress("00:1B:44:33:3A:D8");
        switch2.setDepartment("Network Infrastructure");
        switch2.setStatus("active");
        switch2.setPrice(450.00);
        switch2.setDateLastMaintained(LocalDate.now().minusMonths(1));
        assetService.addAsset(switch2);
    }

    private void createCableAssets() {
        // Cable 1 - Fiber Optic
        Asset cable1 = new Asset();
        cable1.setDeviceName("Fiber Optic Cable - 50m");
        cable1.setMacAddress("N/A");
        cable1.setDepartment("Network Infrastructure");
        cable1.setStatus("active");
        cable1.setPrice(150.00);
        cable1.setDateLastMaintained(LocalDate.now().minusMonths(6));
        assetService.addAsset(cable1);

        // Cable 2 - Ethernet Cable (needs repair - will create expense when marked)
        Asset cable2 = new Asset();
        cable2.setDeviceName("Cat6 Ethernet Cable - 25m");
        cable2.setMacAddress("N/A");
        cable2.setDepartment("Network Infrastructure");
        cable2.setStatus("Needs repair");
        cable2.setPrice(50.00);
        cable2.setDateLastMaintained(LocalDate.now().minusMonths(12));
        assetService.addAsset(cable2);
    }
    
    private void createAssetsForRepairDemo() {
        // Monitor - needs repair (will be marked for repair by user)
        Asset monitor1 = new Asset();
        monitor1.setDeviceName("Dell UltraSharp 27\" Monitor");
        monitor1.setMacAddress("00:1B:44:44:3A:E1");
        monitor1.setDepartment("IT Department");
        monitor1.setStatus("Needs repair");
        monitor1.setPrice(400.00);
        monitor1.setDateLastMaintained(LocalDate.now().minusMonths(9));
        assetService.addAsset(monitor1);
        
        // Printer - needs repair (will be marked for repair by user)
        Asset printer1 = new Asset();
        printer1.setDeviceName("HP LaserJet Pro M404dn");
        printer1.setMacAddress("00:1B:44:44:3A:E2");
        printer1.setDepartment("IT Department");
        printer1.setStatus("Needs repair");
        printer1.setPrice(350.00);
        printer1.setDateLastMaintained(LocalDate.now().minusMonths(7));
        assetService.addAsset(printer1);
    }
}