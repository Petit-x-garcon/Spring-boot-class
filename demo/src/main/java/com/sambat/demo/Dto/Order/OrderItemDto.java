package com.sambat.demo.Dto.Order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemDto {
    @JsonProperty("product_id")
    @NotNull(message = "product id must not empty")
    private Long productId;
    @NotNull(message = "quantity must not empty")
    @Min(value = 1, message = "minimun product is 1")
    private Integer quantity;

}
