package com.konkuk.strhat.global.util;

import com.konkuk.strhat.global.exception.InvalidDateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateParser {
    public static LocalDate parseToLocalDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, formatter);
        }catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(dateStr);
        }
    }
}
