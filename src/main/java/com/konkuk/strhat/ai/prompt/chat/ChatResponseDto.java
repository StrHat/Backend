package com.konkuk.strhat.ai.prompt.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.konkuk.strhat.domain.chat.exception.InvalidChatResultException;
import lombok.Getter;

import static com.konkuk.strhat.ai.prompt.chat.ChatPrompt.ChatResponseFieldNames.*;

@Getter
public class ChatResponseDto {

    @JsonProperty(RESPONSE)
    private String chatResponse;

    public void validateResult() {
        if (this.chatResponse == null || this.chatResponse.isBlank()){
            throw new InvalidChatResultException(chatResponse);
        }
    }
}