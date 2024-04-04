package com.example.newsapi.controller;

import com.example.newsapi.dto.AuthenticationResponseDto;
import com.example.newsapi.dto.AuthenticationUserDto;
import com.example.newsapi.model.LoginDto;
import com.example.newsapi.service.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final SecurityService securityService;

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponseDto> authenticationUser(@RequestBody @Valid LoginDto loginDto){
        return ResponseEntity.ok(securityService.authentication(loginDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registrUser(@RequestBody @Valid AuthenticationUserDto userDto){
        securityService.register(userDto);
        return ResponseEntity.ok("Пользователь зарегистрирован");
    }


}
