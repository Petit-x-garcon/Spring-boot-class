package com.sambat.demo.Dto.Supplier;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"supplier_id", "name", "address", "rating", "email", "phone", "created_at", "updated_at"})
public class SupplierResponseDto {
    @JsonProperty("supplier_id")
    private Long id;
    private String name;
    private String address;
    private String rating;
    private String email;
    private String phone;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
