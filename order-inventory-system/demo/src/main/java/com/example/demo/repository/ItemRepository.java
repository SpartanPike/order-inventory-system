package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // Custom JPA method
    Optional<Item> getItemById(Long id);

    // Native SQL query
    @Query(value = "SELECT * FROM item WHERE id = :id", nativeQuery = true)
    Optional<Item> getItemByIdNative(@Param("id") Long id);
}
