package com.konkuk.strhat.domain.chat.dto;

import com.konkuk.strhat.domain.chat.entity.ChatMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageResponse {
    private final String content;
    private final String sender;
    private final LocalDateTime createdAt;

    public static ChatMessageResponse from(ChatMessage message) {
        return ChatMessageResponse.builder()
                .content(message.getContent())
                .sender(message.getSender().name())
                .createdAt(message.getCreatedAt())
                .build();
    }
}