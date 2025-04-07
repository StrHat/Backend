package com.konkuk.strhat.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GptRequestMessage {

    private String role;
    private String content;

    public static GptRequestMessage system(String content) {
        return new GptRequestMessage("system", content);
    }

    public static GptRequestMessage user(String content) {
        return new GptRequestMessage("user", content);
    }

    public static GptRequestMessage assistant(String content) {
        return new GptRequestMessage("assistant", content);
    }
}
