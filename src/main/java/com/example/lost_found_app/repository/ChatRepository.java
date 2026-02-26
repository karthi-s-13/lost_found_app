package com.example.lost_found_app.repository;

import com.example.lost_found_app.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat, String> {

    Optional<Chat> findByItemIdAndOwnerIdAndClaimerId(
            String itemId,
            String ownerId,
            String claimerId
    );
}