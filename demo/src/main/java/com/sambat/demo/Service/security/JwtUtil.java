package com.sambat.demo.Service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${config.security.secret}")
    private String secret;

    @Value("${config.security.expiration}")
    private long expiration;

    private Key getSignInKey(){
        return Keys.hmacShaKeyFor(this.secret.getBytes());
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();

        return this.createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject){
//        System.out.println("Key: " + this.getSignInKey());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
