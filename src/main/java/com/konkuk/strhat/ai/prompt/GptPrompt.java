package com.konkuk.strhat.ai.prompt;

import com.konkuk.strhat.ai.dto.GptRequestMessage;

import java.util.List;

public interface GptPrompt {
    List<GptRequestMessage> toMessages();
}