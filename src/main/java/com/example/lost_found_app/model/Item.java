package com.example.lost_found_app.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "items")
public class Item {

    @Id
    private String id;

    private String title;
    private String description;
    private String category;

    private String type; // LOST or FOUND
    private String location;
    private String imageUrl;

    @Indexed
    private String userId;

    @Indexed
    private ItemStatus status; // OPEN, CLAIMED, CLOSED, EXPIRED

    @Indexed
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
}