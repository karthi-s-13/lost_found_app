package com.example.lost_found_app.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chats")
public class Chat {

    @Id
    private String id;

    private String itemId;

    private String ownerId;
    private String claimerId;

    private int ownerUnreadCount;
    private int claimerUnreadCount;

    private boolean isActive;

    private LocalDateTime createdAt;
}