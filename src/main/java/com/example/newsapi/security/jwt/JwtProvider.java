package com.example.newsapi.security.jwt;

import com.example.newsapi.security.AppUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    @Value("${app.jwt.secret}")
    private String secret;


    @Value("${app.jwt.expireToken}")
    private Duration expireToken;

    public String generateToken(AppUserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getEmail());
        claims.put("id", userDetails.getUserId());
        claims.put("email", userDetails.getEmail());
        claims.put("firsName", userDetails.getUsername());

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expireToken.toMillis()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean checkValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid token: {}", e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("Token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Token is Unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Claim string is empty: {}", e.getMessage());
        }
        return false;
    }




}
