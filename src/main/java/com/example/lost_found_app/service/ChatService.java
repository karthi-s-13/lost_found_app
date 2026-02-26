package com.example.lost_found_app.service;

import com.example.lost_found_app.model.Chat;
import com.example.lost_found_app.model.Item;
import com.example.lost_found_app.model.Message;
import com.example.lost_found_app.repository.ChatRepository;
import com.example.lost_found_app.repository.ItemRepository;
import com.example.lost_found_app.repository.MessageRepository;
import com.example.lost_found_app.controller.ChatController;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ItemRepository itemRepository;

    // ===============================
    // 🔹 CREATE CHAT
    // ===============================
    public Chat createChat(String itemId, String claimerId) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        String ownerId = item.getUserId();

        if (ownerId.equals(claimerId)) {
            throw new RuntimeException("You cannot start chat with yourself");
        }

        var existingChat = chatRepository
                .findByItemIdAndOwnerIdAndClaimerId(
                        itemId,
                        ownerId,
                        claimerId);

        if (existingChat.isPresent()) {
            return existingChat.get();
        }

        Chat chat = Chat.builder()
                .itemId(itemId)
                .ownerId(ownerId)
                .claimerId(claimerId)
                .ownerUnreadCount(0)
                .claimerUnreadCount(0)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        return chatRepository.save(chat);
    }

    // ===============================
    // 🔹 GET MESSAGES
    // ===============================
    public List<Message> getMessages(String chatId, String userId) {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        if (!chat.getOwnerId().equals(userId)
                && !chat.getClaimerId().equals(userId)) {
            throw new RuntimeException("You are not part of this chat");
        }

        return messageRepository.findByChatIdOrderByTimestampAsc(chatId);
    }

    // ===============================
    // 🔹 SAVE MESSAGE (WebSocket)
    // ===============================
    public Message saveMessage(String chatId,
            String senderId,
            String type,
            String content) {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        if (!chat.isActive()) {
            throw new RuntimeException("Chat is closed");
        }

        if (!chat.getOwnerId().equals(senderId)
                && !chat.getClaimerId().equals(senderId)) {
            throw new RuntimeException("You are not part of this chat");
        }

        // 🔔 Update unread count
        if (chat.getOwnerId().equals(senderId)) {
            chat.setClaimerUnreadCount(chat.getClaimerUnreadCount() + 1);
        } else {
            chat.setOwnerUnreadCount(chat.getOwnerUnreadCount() + 1);
        }

        chatRepository.save(chat);

        Message message = Message.builder()
                .chatId(chatId)
                .senderId(senderId)
                .type(type)
                .content(content)
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return messageRepository.save(message);
    }

    public void resetUnread(String chatId, String userId) {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        if (!chat.getOwnerId().equals(userId)
                && !chat.getClaimerId().equals(userId)) {
            throw new RuntimeException("You are not part of this chat");
        }

        if (chat.getOwnerId().equals(userId)) {
            chat.setOwnerUnreadCount(0);
        } else {
            chat.setClaimerUnreadCount(0);
        }

        chatRepository.save(chat);
    }
}