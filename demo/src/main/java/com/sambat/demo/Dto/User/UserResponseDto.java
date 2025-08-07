package com.sambat.demo.Dto.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"id", "name", "age", "password", "address", "role", "email", "created_at", "updated_at"})
public class UserResponseDto {
    private Long id;
    private String name;
    private Integer age;
    private String password;
    private String address;
    private String role;
    private String email;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
