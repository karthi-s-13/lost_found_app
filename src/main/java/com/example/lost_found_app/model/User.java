package com.example.lost_found_app.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String googleId;
    private String name;
    private String email;

    private String registerNumber;
    private String department;
    private Integer year;

    private String role; // USER / ADMIN
    private String fcmToken;

    private LocalDateTime createdAt;
}