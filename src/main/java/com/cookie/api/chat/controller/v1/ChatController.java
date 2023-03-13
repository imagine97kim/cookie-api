package com.cookie.api.chat.controller.v1;

import com.cookie.api.chat.request.CreateChatRequest;
import com.cookie.api.chat.response.ChatResponse;
import com.cookie.api.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {
    private final ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse createChat(@RequestBody CreateChatRequest request) {
        return chatService.createChat(request);
    }
}
