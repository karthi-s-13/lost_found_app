// package com.example.lost_found_app.controller;

// import com.example.lost_found_app.model.Message;
// import com.example.lost_found_app.service.ChatService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.messaging.handler.annotation.*;
// import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
// import org.springframework.stereotype.Controller;

// @Controller
// @RequiredArgsConstructor
// public class ChatWebSocketController {

//         private final ChatService chatService;

//         @MessageMapping("/send/{chatId}")
//         public void sendMessage(@DestinationVariable String chatId,
//                         @Payload Message message) {

//                 messagingTemplate.convertAndSend(
//                                 "/topic/messages/" + chatId,
//                                 message);
//         }
// }