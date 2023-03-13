package com.cookie.api.chat.service;

import com.cookie.api.chat.request.CreateChatRequest;
import com.cookie.api.chat.response.ChatCompletionResponse;
import com.cookie.api.chat.response.ChatResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ChatService {
    @Value("${OPEN_AI_API_KEY}")
    private String OPEN_AI_API_KEY;
    private final WebClient webClient;
    public ChatService(WebClient.Builder webClientBuilder) {
        this.webClient = WebClient.builder().build();
    }
    public ChatResponse createChat(CreateChatRequest request) {
        ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", new ChatMessage(request.getRole(), request.getContent()));
        Mono<String> aiResponse = webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + OPEN_AI_API_KEY)
                .body(BodyInserters.fromValue(chatRequest))
                .retrieve()
                .bodyToMono(ChatCompletionResponse.class)
                .map(ChatCompletionResponse::getChoices)
                .map(choices -> choices.get(0).getMessage().getContent());

        return ChatResponse.builder()
                .content(aiResponse.block().substring(2))
                .build();
    }

    private static class ChatRequest {
        private final String model;
        private final List<ChatMessage> messages;

        public ChatRequest(String model, ChatMessage message) {
            this.model = model;
            this.messages = Collections.singletonList(message);
        }

        public String getModel() {
            return model;
        }

        public List<ChatMessage> getMessages() {
            return messages;
        }
    }

    private static class ChatMessage {
        private final String role;
        private final String content;

        public ChatMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public String getContent() {
            return content;
        }
    }
}
