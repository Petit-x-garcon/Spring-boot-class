package com.sambat.demo.Dto.Supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierDto {
    @NotBlank(message = "name must be provided")
    @Size(min = 5, max = 50, message = "must between 5 and 50")
    private String name;

    @Size(max = 255, message = "character must not exceed 255")
    private String address;

    @Size(max = 50, message = "rating must not exceed 50 char")
    private String rating;

    @Email(message = "email must valid")
    @Size(max = 50, message = "email must not exceed 50 char")
    private String email;

    @Size(max = 14, message = "phone must not exceed 14 char")
    private String phone;
}
