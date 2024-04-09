package com.example.newsapi.repository;

import com.example.newsapi.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    @Transactional
    void deleteAllByExpireDateBefore(Instant time);
    @Transactional
    void deleteRefreshTokenByUserId(Long userId);
    boolean existsByToken(String token);

    boolean existsAllByExpireDateBefore(Instant time);

    Optional<RefreshToken> findByToken(String token);
}
