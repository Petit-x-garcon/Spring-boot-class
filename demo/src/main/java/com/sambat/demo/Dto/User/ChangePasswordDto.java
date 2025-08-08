package com.sambat.demo.Dto.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @NotBlank(message = "password must not empty")
    @Size(min = 4, max = 20, message = "size must be between 4 and 20")
    private String oldPassword;

    @NotBlank(message = "password must not empty")
    @Size(min = 4, max = 20, message = "size must be between 4 and 20")
    private String newPassword;

    @NotBlank(message = "password must not empty")
    @Size(min = 4, max = 20, message = "size must be between 4 and 20")
    private String confirmPassword;
}
