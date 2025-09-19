package com.sambat.demo.Service.security;

import com.sambat.demo.Common.config.ApplicationConfiguration;
import com.sambat.demo.Entity.RefreshTokenEntity;
import com.sambat.demo.Entity.UserEntity;
import com.sambat.demo.Exception.Model.NotFoundHandler;
import com.sambat.demo.Repository.RefreshTokenRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private ApplicationConfiguration appCofig;

    private long expiration;
    @PostConstruct
    private void init(){
        this.expiration = appCofig.getSecurity().getRefreshTokenExpiration();
    }

    public RefreshTokenEntity createRefreshToken(UserEntity user){
        String refreshToken = this.generateRefreshToken();

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpireAt(LocalDateTime.now().plusHours(expiration));
        refreshTokenEntity.setUser(user);

        return refreshTokenRepository.save(refreshTokenEntity);
    }

    public String generateRefreshToken(){
        SecureRandom token = new SecureRandom();
        byte[] tokenByte = new byte[64];
        token.nextBytes(tokenByte);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenByte);
    }

    public RefreshTokenEntity getToken(String payload){
        return refreshTokenRepository.findByToken(payload)
                .orElseThrow(() -> new NotFoundHandler("token doesn't exist."));
    }

    public RefreshTokenEntity rotateToken(RefreshTokenEntity token, UserEntity user){
        token.setRevoked(true);
        return this.createRefreshToken(user);
    }
}
