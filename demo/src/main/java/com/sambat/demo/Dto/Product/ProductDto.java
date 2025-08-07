package com.sambat.demo.Dto.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProductDto {
    @NotBlank(message = "do not leave blank name")
    private String name;
    private String description;

    @NotNull(message = "do not leave field null")
    @Positive(message = "the value must be positive")
    private Double price;
}
