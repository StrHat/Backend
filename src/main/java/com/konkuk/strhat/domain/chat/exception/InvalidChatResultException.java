package com.konkuk.strhat.domain.chat.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class InvalidChatResultException extends CustomException {

    public InvalidChatResultException(String content) {
        super(ErrorCode.INVALID_CHAT_RESULT, "응답 내용: " + content);
    }
}
