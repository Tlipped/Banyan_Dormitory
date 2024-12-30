package com.banyan_dormitory.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringUtil {
    public  static  boolean isEmpty(String text){
        return text==null|| text.trim().isEmpty();
    }
    public static boolean isNumeric(String phoneNumber) {
        return phoneNumber.matches("\\d+");
    }
    public static boolean containsDigit(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches(".*\\d.*");
    }
}
