package com.example.lost_found_app.controller;

import com.example.lost_found_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/fcm-token")
    public String saveFcmToken(@RequestParam String token,
                               Authentication authentication) {

        String userId = authentication.getPrincipal().toString();

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFcmToken(token);
        userRepository.save(user);

        return "FCM token saved";
    }
}