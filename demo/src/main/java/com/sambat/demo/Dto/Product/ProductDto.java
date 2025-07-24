package com.sambat.demo.Dto.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProductDto {
    private String name;
    private String description;
    private Double price;
}
