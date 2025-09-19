package com.sambat.demo.Common.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config")
@Data
public class ApplicationConfiguration {
    private SecurityProperties security;

    @Getter
    @Setter
    public static class SecurityProperties{
        private String secret;
        private long expiration;
        private long refreshTokenExpiration;
    }
}
