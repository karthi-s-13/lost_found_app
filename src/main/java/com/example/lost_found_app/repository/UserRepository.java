package com.example.lost_found_app.repository;

import com.example.lost_found_app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByGoogleId(String googleId);
    Optional<User> findByEmail(String email);
}