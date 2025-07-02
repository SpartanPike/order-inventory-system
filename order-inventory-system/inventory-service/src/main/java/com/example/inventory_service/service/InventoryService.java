package com.example.inventory_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.inventory_service.repository.ProductInventoryRepository;

@Service
public class InventoryService {

    @Autowired
    private ProductInventoryRepository repository;

    public boolean isInStock(String productName, int quantityRequested) {
        return repository.findByProductNameIgnoreCase(productName)
                .map(item -> item.getAvailableQuantity() >= quantityRequested)
                .orElse(false);
    }
}
