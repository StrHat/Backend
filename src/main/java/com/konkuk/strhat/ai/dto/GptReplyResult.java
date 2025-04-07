package com.konkuk.strhat.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GptReplyResult {
    private String status;
    private String type;
    private String text;
}