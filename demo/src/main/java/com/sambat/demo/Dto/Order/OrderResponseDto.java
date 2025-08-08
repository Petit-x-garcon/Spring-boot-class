package com.sambat.demo.Dto.Order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sambat.demo.Repository.OrderItemRepository;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    @JsonProperty("order_id")
    private Long id;

    private String status;
    private Double total;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("items")
    private List<OrderItemResponseDto> items;
}
