package com.example.newsapi.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {


        log.error("JwtAuthenticationEntryPoint: " + authException.toString());

        Arrays.stream(authException.getStackTrace()).forEach(err -> log.error("JwtAuthenticationEntryPoint: " + err + "\n"));


        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> body = new HashMap<>();
        body.put("Status", HttpStatus.UNAUTHORIZED.value());
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());
        objectMapper.writeValue(response.getOutputStream(),body);

    }
}
