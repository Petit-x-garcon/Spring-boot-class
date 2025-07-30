package com.sambat.demo.Dto.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"id", "name", "description", "price", "created_at", "updated_at"})
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    @JsonProperty("total_stock")
    private Long totalStock;
    @JsonProperty("created_At")
    private LocalDateTime createdAt;
    @JsonProperty("updated_At")
    private LocalDateTime updatedAt;
}
