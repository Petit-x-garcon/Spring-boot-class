package com.sambat.demo.Dto.Stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"id", "product_id", "quantity", "created_at", "updated_at"})
public class StockResponseDto {
    private Long id;
    private Long productId;
    private Long quantity;
    @JsonProperty("created_At")
    private LocalDateTime createdAt;
    @JsonProperty("updated_At")
    private LocalDateTime updatedAt;
}
