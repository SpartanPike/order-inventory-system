package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ItemDTO;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ItemDTO> getAllItems() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public ItemDTO createItem(ItemDTO itemDto) {
        if (itemDto.getPrice() <= 0) {
            throw new IllegalArgumentException("Item price must be greater than zero.");
        }
        Item item = modelMapper.map(itemDto, Item.class);
        Item saved = repository.save(item);
        return convertToDTO(saved);
    }

    public ItemDTO updateItem(Long id, ItemDTO itemDto) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(itemDto.getName());
                    existing.setQuantity(itemDto.getQuantity());
                    existing.setPrice(itemDto.getPrice());
                    Item updated = repository.save(existing);
                    return convertToDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public Optional<ItemDTO> getItemById(Long id) {
        return repository.findById(id)
                .map(this::convertToDTO);
    }

    public ItemDTO convertToDTO(Item item) {
        return modelMapper.map(item, ItemDTO.class);
    }
}
