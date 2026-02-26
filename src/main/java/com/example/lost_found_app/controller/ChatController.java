package com.example.lost_found_app.controller;

import com.example.lost_found_app.model.Message;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send/{chatId}")
    public void sendMessage(@DestinationVariable String chatId,
                            @Payload Message message) {

        messagingTemplate.convertAndSend(
                "/topic/messages/" + chatId,
                message
        );
    }
}