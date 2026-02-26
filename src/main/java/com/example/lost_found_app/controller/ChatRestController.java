// package com.example.lost_found_app.controller;

// import com.example.lost_found_app.model.Chat;
// import com.example.lost_found_app.model.Message;
// import com.example.lost_found_app.service.ChatService;

// import lombok.RequiredArgsConstructor;

// import org.springframework.security.core.Authentication;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/chat")
// @RequiredArgsConstructor
// public class ChatRestController {

//     private final ChatService chatService;

//     // 🔹 START CHAT
//     @PostMapping("/start/{itemId}")
//     public Chat startChat(@PathVariable String itemId,
//             Authentication authentication) {

//         String userId = authentication.getPrincipal().toString();
//         return chatService.createChat(itemId, userId);
//     }

//     // 🔹 GET MESSAGES
//     @GetMapping("/{chatId}/messages")
//     public List<Message> getMessages(@PathVariable String chatId,
//             Authentication authentication) {

//         String userId = authentication.getPrincipal().toString();
//         return chatService.getMessages(chatId, userId);
//     }

//     @PutMapping("/{chatId}/reset-unread")
//     public String resetUnread(@PathVariable String chatId,
//             Authentication authentication) {

//         String userId = authentication.getPrincipal().toString();
//         chatService.resetUnread(chatId, userId);

//         return "Unread count reset";
//     }
// }