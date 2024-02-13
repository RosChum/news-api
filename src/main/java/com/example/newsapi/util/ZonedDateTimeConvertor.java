package com.example.newsapi.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class ZonedDateTimeConvertor  {

public ZonedDateTime convertToZonedDateTime(String source){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    LocalDate date = LocalDate.parse(source, formatter);
    ZonedDateTime result = date.atStartOfDay(ZoneId.systemDefault());
    return result;



}

}
