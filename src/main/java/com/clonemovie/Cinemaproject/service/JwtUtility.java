package com.clonemovie.Cinemaproject.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtility {

    private String secret = "yourSecretKey"; // 비밀 키 설정

    private static final long EXPIRATION_TIME = 1000L * 60 * 60; // 만료 시간 (1시간)

    // JWT 생성
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8)) // 서명
                .compact();
    }

    // 자식 토큰 생성
    public String ChildGenerateToken(String uniqueKey) {
        return Jwts.builder()
                .setSubject(uniqueKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8)) // 서명
                .compact();
    }

    // JWT 검증
    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8)) // 서명 키 설정
                    .parseClaimsJws(token) // JWT 파싱 및 검증
                    .getBody();
        } catch (Exception ex) {
            throw new RuntimeException("JWT 검증에 실패했습니다: " + ex.getMessage()); // 에러 처리
        }
    }
}