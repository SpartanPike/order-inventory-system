package com.example.demo.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {

    @Autowired
    private ItemRepository repository;

    @PostConstruct
    public void loadData() {
        repository.saveAll(List.of(
                new Item(null, "Notebook", 50, 25.0),
                new Item(null, "Pen", 200, 5.5)
        ));
    }
}
