package com.konkuk.strhat.ai.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class GptResponseParseException extends CustomException {

    public GptResponseParseException(String message) {
        super(ErrorCode.GPT_RESPONSE_PARSE_FAIL, message);
    }
}
