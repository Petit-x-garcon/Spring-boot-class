package com.sambat.demo.Dto.User;

import com.sambat.demo.Common.Annotations.ValidEnum;
import com.sambat.demo.Common.Enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDto {
    @NotBlank(message = "name must be provided")
    @Size(min = 5, max = 50, message = "must between 5 and 50")
    private String name;

    @Min(value = 12, message = "user must be above 12 years old")
    private Integer age;

    @Size(max = 255, message = "character must not exceed 255")
    private String address;

    @ValidEnum(enumClass = Role.class, message = "role must be user or admin")
    private String role;

    @Email(message = "email must valid")
    @Size(max = 50, message = "email must not exceed 50 char")
    private String email;
}
