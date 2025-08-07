package com.sambat.demo.Dto.User;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank(message = "name must be provided")
    @Size(min = 5, max = 50, message = "must between 5 and 50")
    private String name;

    @Min(value = 12, message = "user must be above 12 years old")
    private Integer age;

    @NotBlank(message = "password must not empty")
    @Size(min = 4, max = 20, message = "size must be between 4 and 20")
    private String password;

    @Size(max = 255, message = "character must not exceed 255")
    private String address;

    private String role;

    @Email(message = "email must valid")
    @Size(max = 50, message = "email must not exceed 50 char")
    private String email;
}
