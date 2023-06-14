package com.example.soonsul.user.oauth.jwt;

import com.example.soonsul.user.entity.User;
import com.example.soonsul.user.redis.RefreshToken;
import io.jsonwebtoken.*;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateJwtToken(User user) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getNickname())
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setExpiration(createExpireHourForOneYear())
                .signWith(SignatureAlgorithm.HS256, createSigningKey());
        return builder.compact();
    }

    public RefreshToken generateJwtRefreshToken(User user) {
        return new RefreshToken(UUID.randomUUID().toString(), user.getUserId());
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    private static Date createExpireHourForOneYear() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 2);
        return c.getTime();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private static Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nickname", user.getNickname());
        return claims;
    }

    private Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            return true;
        } catch (JwtException | NullPointerException exception) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (Long) claims.get("userId");
    }

}