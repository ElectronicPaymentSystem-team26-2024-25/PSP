package com.psp.psp.auth.service;

import com.psp.psp.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 15 * 60 * 1000;

    public String GenerateJwtToken(User user){
        Date now = new Date();

        String jwt = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("role", user.getType())
                .claim("email", user.getEmail())
                .signWith(secretKey)
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
                .compact();
        return jwt;
    }
}
