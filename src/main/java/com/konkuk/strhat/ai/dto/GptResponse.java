package com.konkuk.strhat.ai.dto;

import lombok.Getter;

import java.util.List;

// OpenAI의 GPT Chat Completion API 응답 형식에 맞춘 구조
@Getter
public class GptResponse {
    private String id;
    private String model;
    private List<Output> output;

    @Getter
    public static class Output {
        private String type;
        private String id;
        private String status;
        private String role;
        private List<Content> content;
    }

    @Getter
    public static class Content {
        private String type;
        private String text;
    }
}