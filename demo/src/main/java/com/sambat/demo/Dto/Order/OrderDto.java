package com.sambat.demo.Dto.Order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    @JsonProperty("items")
    @NotEmpty(message = "order item should not be empty")
    private List<OrderItemDto> orderItemDtoList;
}
