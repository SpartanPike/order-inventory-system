package com.example.inventory_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory_service.model.ProductInventory;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {
    Optional<ProductInventory> findByProductNameIgnoreCase(String productName);
}
