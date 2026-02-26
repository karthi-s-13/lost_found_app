package com.example.lost_found_app.repository;

import com.example.lost_found_app.model.Item;
import com.example.lost_found_app.model.ItemStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    List<Item> findByUserId(String userId);

    List<Item> findByExpiresAtBeforeAndStatus(
            LocalDateTime now,
            ItemStatus status
    );
}