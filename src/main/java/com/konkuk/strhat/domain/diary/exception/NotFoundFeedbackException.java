package com.konkuk.strhat.domain.diary.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class NotFoundFeedbackException extends CustomException {

    public NotFoundFeedbackException() {
        super(ErrorCode.NOT_FOUND_RESOURCE, "피드백 데이터가 존재하지 않습니다.");
    }
}
