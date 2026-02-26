package com.example.lost_found_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank
    private String idToken; // Google ID Token

    private String registerNumber;
    private String department;
    private Integer year;
}