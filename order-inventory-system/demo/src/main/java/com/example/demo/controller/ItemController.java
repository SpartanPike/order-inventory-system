package com.example.demo.controller;

import com.example.demo.client.InventoryClient;
import com.example.demo.dto.ItemDTO;
import com.example.demo.repository.ItemCriteriaRepository;
import com.example.demo.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemCriteriaRepository itemCriteriaRepo;

    @Autowired
    private InventoryClient inventoryClient; // ✅ Injected Feign client

    // ✅ Get all items as DTOs
    @GetMapping
    public List<ItemDTO> getAllItems() {
        return itemService.getAllItems();
    }

    // ✅ Filtered by min price, return DTOs
    @GetMapping("/filter")
    public List<ItemDTO> filterItemsByMinPrice(@RequestParam double minPrice) {
        return itemCriteriaRepo.findItemsByPriceGreaterThan(minPrice).stream()
                .map(item -> itemService.convertToDTO(item))
                .toList();
    }

    @GetMapping("/filter/price-above/{amount}")
    public List<ItemDTO> getItemsByPrice(@PathVariable double amount) {
        return itemCriteriaRepo.findItemsByPriceGreaterThan(amount).stream()
                .map(item -> itemService.convertToDTO(item))
                .toList();
    }

    // ✅ Get by ID, return DTO
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST - accepts and returns DTO
    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDto) {
        ItemDTO saved = itemService.createItem(itemDto);
        return ResponseEntity.ok(saved);
    }

    // ✅ PUT - accepts DTO, updates existing, and returns DTO
    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDto) {
        try {
            ItemDTO updated = itemService.updateItem(id, itemDto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ New: Feign client test endpoint
    @GetMapping("/check-stock")
    public String checkItemStock(@RequestParam String product, @RequestParam int qty) {
        boolean available = inventoryClient.checkStock(product, qty);
        return available ? "In stock ✅" : "Out of stock ❌";
    }
}
