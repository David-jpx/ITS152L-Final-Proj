package com.example.demo.repository;
import com.example.demo.model.Asset;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssetRepository extends CrudRepository<Asset, Long> {
    List<Asset> findByDepartment(String department); // NEW
    List<Asset> findByStatus(String status); // NEW
}