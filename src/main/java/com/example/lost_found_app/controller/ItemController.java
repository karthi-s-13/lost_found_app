package com.example.lost_found_app.controller;

import com.example.lost_found_app.dto.ItemRequest;
import com.example.lost_found_app.model.Item;
import com.example.lost_found_app.service.ItemService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public Item createItem(@Valid @RequestBody ItemRequest request,
                           Authentication authentication) {

        String userId = authentication.getPrincipal().toString();
        return itemService.createItem(request, userId);
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/my")
    public List<Item> getMyItems(Authentication authentication) {

        String userId = authentication.getPrincipal().toString();
        return itemService.getMyItems(userId);
    }

    @PutMapping("/{id}/status")
    public Item updateStatus(@PathVariable String id,
                             @RequestParam String status,
                             Authentication authentication) {

        String userId = authentication.getPrincipal().toString();
        return itemService.updateStatus(id, status, userId);
    }
}