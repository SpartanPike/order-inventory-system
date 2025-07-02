package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-client", url = "http://localhost:8080")
public interface InventoryClient {
    @GetMapping("/inventory/check")
    boolean checkStock(@RequestParam("product") String product,
                       @RequestParam("qty") int quantity);
}
