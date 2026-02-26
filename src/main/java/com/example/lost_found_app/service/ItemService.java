package com.example.lost_found_app.service;

import com.example.lost_found_app.dto.ItemRequest;
import com.example.lost_found_app.model.Item;
import com.example.lost_found_app.model.ItemStatus;
import com.example.lost_found_app.repository.ItemRepository;
import com.example.lost_found_app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    // 🔹 CREATE ITEM
    public Item createItem(ItemRequest request, String userId) {

        Item item = Item.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .type(request.getType())
                .location(request.getLocation())
                .imageUrl(request.getImageUrl())
                .userId(userId)
                .status(ItemStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(30))
                .build();

        Item savedItem = itemRepository.save(item);

        // 🔔 Send notification to all users
        var users = userRepository.findAll();

        for (var user : users) {
            if (user.getFcmToken() != null && !user.getId().equals(userId)) {
                notificationService.sendNotification(
                        user.getFcmToken(),
                        "New " + request.getType() + " Item",
                        request.getTitle() + " at " + request.getLocation()
                );
            }
        }

        return savedItem;
    }

    // 🔹 GET ALL ITEMS
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // 🔹 GET MY ITEMS
    public List<Item> getMyItems(String userId) {
        return itemRepository.findByUserId(userId);
    }

    // 🔹 UPDATE STATUS
    public Item updateStatus(String itemId, String newStatus, String userId) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Only owner can update
        if (!item.getUserId().equals(userId)) {
            throw new RuntimeException("You are not allowed to update this item");
        }

        ItemStatus status;

        try {
            status = ItemStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value");
        }

        item.setStatus(status);

        return itemRepository.save(item);
    }

    // 🔹 AUTO EXPIRE ITEMS DAILY AT MIDNIGHT
    @Scheduled(cron = "0 0 0 * * ?")
    public void expireOldItems() {

        List<Item> expiredItems =
                itemRepository.findByExpiresAtBeforeAndStatus(
                        LocalDateTime.now(),
                        ItemStatus.OPEN
                );

        for (Item item : expiredItems) {
            item.setStatus(ItemStatus.EXPIRED);
            itemRepository.save(item);
        }
    }
}