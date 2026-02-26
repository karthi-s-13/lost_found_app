package com.example.lost_found_app.service;

import com.example.lost_found_app.dto.LoginRequest;
import com.example.lost_found_app.model.User;
import com.example.lost_found_app.repository.UserRepository;
import com.example.lost_found_app.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public String login(LoginRequest request) {

        // TODO: Verify Google ID token properly
        String googleId = request.getIdToken(); // temporary mock

        User user = userRepository.findByGoogleId(googleId)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .googleId(googleId)
                            .name("Temp Name")
                            .email("temp@email.com")
                            .registerNumber(request.getRegisterNumber())
                            .department(request.getDepartment())
                            .year(request.getYear())
                            .role("USER")
                            .createdAt(LocalDateTime.now())
                            .build();
                    return userRepository.save(newUser);
                });

        return jwtUtil.generateToken(user.getId());
    }
}