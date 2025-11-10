package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "assets")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String deviceName;
    private String macAddress;
    private String department;
    private String status;  // e.g., "pending", "approved", "rejected", "active"
    private double price;
    private LocalDate dateLastMaintained;

    public Asset() {}

    public Asset(long id, String deviceName, String macAddress, String department, String status, double price, LocalDate dateLastMaintained) {
        this.id = id;
        this.deviceName = deviceName;
        this.macAddress = macAddress;
        this.department = department;
        this.status = status;
        this.price = price;
        this.dateLastMaintained = dateLastMaintained;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public LocalDate getDateLastMaintained() { return dateLastMaintained; }
    public void setDateLastMaintained(LocalDate dateLastMaintained) { this.dateLastMaintained = dateLastMaintained; }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceName, macAddress, department, status, price, dateLastMaintained);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Asset asset = (Asset) obj;
        return Objects.equals(id, asset.id) &&
               Objects.equals(deviceName, asset.deviceName) &&
               Objects.equals(macAddress, asset.macAddress) &&
               Objects.equals(department, asset.department) &&
               Objects.equals(status, asset.status) &&
               Objects.equals(price, asset.price) &&
               Objects.equals(dateLastMaintained, asset.dateLastMaintained);
    }

    @Override
    public String toString() {
        return "Asset{" +
               "id=" + id +
               ", deviceName='" + deviceName + '\'' +
               ", macAddress='" + macAddress + '\'' +
               ", department='" + department + '\'' +
               ", status='" + status + '\'' +
               ", price=" + price +
               ", dateLastMaintained=" + dateLastMaintained +
               '}';
    }
}