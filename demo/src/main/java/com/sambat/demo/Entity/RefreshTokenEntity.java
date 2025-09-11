package com.sambat.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Data
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String token;
    @Column(name = "expire_at", nullable = false)
    private LocalDateTime expireAt;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean revoked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    public boolean isExpired(){
        return this.expireAt.isBefore(LocalDateTime.now());
    }

    public boolean isValidate(){
        return !this.revoked && !this.isExpired();
    }
}
