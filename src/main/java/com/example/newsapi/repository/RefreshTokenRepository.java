package com.example.newsapi.repository;

import com.example.newsapi.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    void deleteAllByExpireDateAfter(Instant time);
    void deleteRefreshTokenByUserId(Long userId);
    boolean existsByToken(String token);

    Optional<RefreshToken> findByToken(String token);
}
