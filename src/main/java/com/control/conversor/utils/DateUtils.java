package com.control.conversor.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtils {

     public LocalDateTime toLocalDateTime(String dateToConvert) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return LocalDateTime
                .parse(dateToConvert, formatter)
                .atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
                .toLocalDateTime();
    }

    public LocalDate convertLocalDateTimeToLocalDate(LocalDateTime localDateTime) {
       return localDateTime.toLocalDate();
    }

    public LocalDate toLocalDate(String date){
        return convertLocalDateTimeToLocalDate(toLocalDateTime(date));
    }
}
