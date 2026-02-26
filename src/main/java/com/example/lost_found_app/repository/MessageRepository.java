package com.example.lost_found_app.repository;

import com.example.lost_found_app.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findByChatIdOrderByTimestampAsc(String chatId);
}