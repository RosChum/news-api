package com.example.newsapi.repository;

import com.example.newsapi.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    void deleteAllByCreateAtAfter(Duration expireRefreshToken);
    void deleteRefreshTokenByUserId(Long userId);
    boolean existsByToken(String token);
}
