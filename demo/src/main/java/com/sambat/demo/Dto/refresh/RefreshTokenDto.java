package com.sambat.demo.Dto.refresh;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenDto {
    @JsonProperty("refresh_token")
    @NotNull(message = "token is required")
    @NotBlank(message = "token must not be blank")
    private String refreshToken;
}
