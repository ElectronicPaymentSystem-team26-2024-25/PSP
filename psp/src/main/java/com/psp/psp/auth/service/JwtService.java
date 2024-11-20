package com.psp.psp.auth.service;

import com.psp.psp.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    private static final long EXPIRATION_TIME = 15 * 60 * 1000;

    public String generateJwtToken(User user){
        Date now = new Date();

        String jwt = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("role", user.getType())
                .claim("email", user.getEmail())
                .signWith(getSigningKey())
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
                .compact();
        return jwt;
    }

    public boolean validateJwtToken(String jwt){
        try {
            Claims claims = extractClaims(jwt);
            if(claims.isEmpty()) return false;
            return !isJwtTokenExpired(claims);
        } catch (SignatureException | IllegalArgumentException ex) {
            return false;
        }
    }

    public Claims extractClaims(String jwt){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private boolean isJwtTokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
