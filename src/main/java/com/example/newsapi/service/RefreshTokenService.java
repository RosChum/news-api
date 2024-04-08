package com.example.newsapi.service;

import com.example.newsapi.exception.ContentNotFound;
import com.example.newsapi.model.RefreshToken;
import com.example.newsapi.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    @Value("${app.jwt.expireRefreshToken}")
    private Duration expireRefreshToken;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createTokenByUserId(Long userId){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUserId(userId);
        refreshToken.setExpireDate(Instant.now().plus(expireRefreshToken));
      return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(()-> new ContentNotFound("Refresh token not found. Please, repeat signin"));
    }

    public boolean checkExpireRefreshToken(RefreshToken token){
        if (token.getExpireDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            return false;
        }
        return true;
    }

    public void deleteByUserId(Long userId){
        refreshTokenRepository.deleteRefreshTokenByUserId(userId);
    }


    @Async
    @Scheduled(cron = "@midnight")
    public void deleteTokenByExpiredToken(){
        log.info("deleteTokenByExpiredToken " + refreshTokenRepository.existsAllByExpireDateBefore(Instant.now()));
        if (refreshTokenRepository.existsAllByExpireDateBefore(Instant.now())) {
            refreshTokenRepository.deleteAllByExpireDateBefore(Instant.now());
        }
    }





}
