package com.konkuk.strhat.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ChatResponse {

    private final String message;

    public static ChatResponse of(String message) {
        return ChatResponse.builder()
                .message(message)
                .build();
    }
}