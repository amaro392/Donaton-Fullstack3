package com.donaton.logistics.controller;

import com.donaton.logistics.model.Inventory;
import com.donaton.logistics.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistics")
public class LogisticsController {

    @Autowired
    private InventoryRepository repository;

    @GetMapping("/inventory")
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    @PostMapping("/inventory")
    public Inventory addInventory(@RequestBody Inventory inventory) {
        return repository.save(inventory);
    }
}