package com.example.newsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessRightsException extends RuntimeException{
    public AccessRightsException(String message) {

    }
}
