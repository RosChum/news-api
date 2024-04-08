package com.example.newsapi.repository;

import com.example.newsapi.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    @Query(value = "DELETE FROM refresh_token WHERE expire_date < :time", nativeQuery = true)
    void deleteAllByExpireDateAfter(Instant time);
    void deleteRefreshTokenByUserId(Long userId);
    boolean existsByToken(String token);

    boolean existsAllByExpireDateAfter(Instant time);

    Optional<RefreshToken> findByToken(String token);
}
