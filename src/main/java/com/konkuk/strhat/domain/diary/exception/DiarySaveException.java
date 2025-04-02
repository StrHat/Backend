package com.konkuk.strhat.domain.diary.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

public class DiarySaveException extends CustomException {

    public DiarySaveException() {
        super(ErrorCode.DATABASE_SAVE_ERROR, "일기 데이터를 저장하는 도중 오류가 발생했습니다.");
    }
}
