package com.konkuk.strhat.domain.chat.dto;

import com.konkuk.strhat.domain.chat.entity.ChatMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageLogResponse {
    private final List<ChatMessageResponse> chatMessages;

    public static ChatMessageLogResponse from(List<ChatMessage> messageList) {
        return ChatMessageLogResponse.builder()
                .chatMessages(messageList.stream()
                        .map(ChatMessageResponse::from)
                        .toList())
                .build();
    }
}
