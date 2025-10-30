package com.sambat.demo.Common.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "config")
@Data
public class ApplicationConfiguration {
    private SecurityProperties security;
    private Pagination pagination;
    private JsonPlaceholder jsonPlaceholder;

    @Getter
    @Setter
    public static class SecurityProperties{
        private String secret;
        private long expiration;
        private long refreshTokenExpiration;
    }

    @Getter
    @Setter
    public static class Pagination{
        private String baseUrl;
        private Map<String, String> uri;

        public String buildUrl(String resource){
            return baseUrl + uri.getOrDefault(resource, "");
        }
    }

    @Getter
    @Setter
    public static class JsonPlaceholder{
        private String baseUrl;
        private Map<String, String> uri;

        public String getComment(){
            return uri.get("comments");
        }
    }
}
