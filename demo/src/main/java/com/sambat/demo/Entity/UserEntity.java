package com.sambat.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String role;
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
