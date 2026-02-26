package com.example.lost_found_app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test(Authentication authentication) {

        // If JWT is valid, authentication will not be null
        if (authentication == null) {
            return "No Authentication Found";
        }

        String userId = authentication.getPrincipal().toString();

        return "Authenticated User ID: " + userId;
    }
}