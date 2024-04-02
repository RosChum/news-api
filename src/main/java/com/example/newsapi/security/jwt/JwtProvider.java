package com.example.newsapi.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class JwtProvider {

    @Value("${app.jwt.secret}")
    private String secret;


    @Value("${app.jwt.expireToken}")
    private Duration expireToken;




}
