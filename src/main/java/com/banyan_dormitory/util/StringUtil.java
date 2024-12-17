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
    //将字符串日期转化为date和time
    public static String[] splitAndConvertDateTime(String dateTimeString) {
        DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter dateOnlyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeOnlyFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            // Try parsing as full datetime
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, fullFormatter);
            return new String[]{localDateTime.toLocalDate().toString(), localDateTime.toLocalTime().toString()};
        } catch (DateTimeParseException e1) {
            try {
                // Try parsing as date only
                LocalDate datePart = LocalDate.parse(dateTimeString, dateOnlyFormatter);
                LocalTime currentTime = LocalTime.now();
                return new String[]{datePart.toString(), currentTime.format(timeOnlyFormatter)};
            } catch (DateTimeParseException e2) {
                try {
                    // Try parsing as time only
                    LocalTime timePart = LocalTime.parse(dateTimeString, timeOnlyFormatter);
                    LocalDate currentDate = LocalDate.now();
                    return new String[]{currentDate.toString(), timePart.format(timeOnlyFormatter)};
                } catch (DateTimeParseException e3) {
                    // If all parses fail, return null indicating an error in format
                    return null;
                }
            }
        }
    }
}
