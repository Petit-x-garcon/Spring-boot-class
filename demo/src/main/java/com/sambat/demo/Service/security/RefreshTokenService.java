package com.sambat.demo.Service.security;

import com.sambat.demo.Dto.refresh.RefreshTokenDto;
import com.sambat.demo.Entity.RefreshTokenEntity;
import com.sambat.demo.Entity.UserEntity;
import com.sambat.demo.Exception.Model.NotFoundHandler;
import com.sambat.demo.Repository.RefreshTokenRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenEntity createRefreshToken(UserEntity user){
        String refreshToken = UUID.randomUUID().toString();

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpireAt(LocalDateTime.now().plusHours(2));
        refreshTokenEntity.setUser(user);

        return refreshTokenRepository.save(refreshTokenEntity);
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
