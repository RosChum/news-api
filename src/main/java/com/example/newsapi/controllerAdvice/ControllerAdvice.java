package com.example.newsapi.controllerAdvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

@ExceptionHandler(Exception.class)
    public void exceptionInfo(Exception exception){
    log.info(exception.toString());
    StringBuilder stringBuilder = new StringBuilder();
    Arrays.stream(exception.getStackTrace()).forEach(stackTraceElement -> stringBuilder.append(stackTraceElement.toString()).append("\n"));
    log.info(stringBuilder.toString());
}

}
