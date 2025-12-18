package com.martinalba.tsg_challenge.service;

import com.martinalba.tsg_challenge.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class JwtService {

    @Value("${JWT_SECRET}")
    private String jwtSecret;
    @Value("${JWT_EXPIRATION}")
    private long jwtExpirationMs;
    @Value("${JWT_REFRESH_EXPIRATION}")
    private long jwtRefreshExpirationMs;

    // Generate JWT token
    public String generateAccessToken(User user) {
        return buildToken(user, jwtExpirationMs);
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, jwtRefreshExpirationMs);
    }

    private String buildToken(final User user, final long expirationMs) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("username", user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, User user) {
        return extractUsername(token).equals(user.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
