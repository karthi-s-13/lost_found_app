package com.example.lost_found_app.controller;

import java.util.Map;
import com.example.lost_found_app.dto.LoginRequest;
import com.example.lost_found_app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return Map.of("token", token);
}
}