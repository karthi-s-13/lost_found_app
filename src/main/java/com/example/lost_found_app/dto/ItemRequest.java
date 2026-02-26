package com.example.lost_found_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ItemRequest {

    @NotBlank
    private String title;

    private String description;
    private String category;

    @NotBlank
    private String type; // LOST / FOUND

    @NotBlank
    private String location;

    private String imageUrl;
}