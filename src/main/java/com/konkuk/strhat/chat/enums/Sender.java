package com.konkuk.strhat.chat.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sender {
    USER("유저"),
    CHAT_BOT("챗봇");

    private final String description;
}
