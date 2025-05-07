package com.konkuk.strhat.global.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {

    // 한 주의 시작을 월요일, 한 주의 끝을 일요일로 가정.

    /**
     * 주어진 날짜를 기준으로 '해당 주의 월요일' 반환
     */
    public static LocalDate getStartOfWeek(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    /**
     * 주어진 날짜를 기준으로 '해당 주의 일요일' 반환
     */
    public static LocalDate getEndOfWeek(LocalDate date) {
        return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }
}
