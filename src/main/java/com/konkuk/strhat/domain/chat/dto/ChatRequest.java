package com.konkuk.strhat.domain.chat.dto;

import com.konkuk.strhat.domain.chat.enums.ChatMode;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class ChatRequest {

    @NotBlank
    @Length(min = 1, max = 255)
    private String userMessage;

    @NotBlank
    private ChatMode chatMode;
}
