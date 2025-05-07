package com.konkuk.strhat.domain.stressScore.exception;

import com.konkuk.strhat.global.error.CustomException;
import com.konkuk.strhat.global.error.ErrorCode;

import java.time.LocalDate;

public class NoDiaryInWeekException extends CustomException {
    public NoDiaryInWeekException(LocalDate startDate, LocalDate endDate) {
        super(ErrorCode.NO_DIARY_IN_WEEK, "요청 날짜: " + startDate + "~" + endDate);
    }
}
