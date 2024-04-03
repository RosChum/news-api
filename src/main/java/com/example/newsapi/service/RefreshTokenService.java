package com.example.newsapi.service;

import com.example.newsapi.model.RefreshToken;
import com.example.newsapi.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${app.jwt.expireRefreshToken}")
    private Duration expireRefreshToken;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createByUserId(Long userId){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUserId(userId);
        refreshToken.setExpireDate(Instant.now().plus(expireRefreshToken));
      return refreshTokenRepository.save(refreshToken);
    }


}
