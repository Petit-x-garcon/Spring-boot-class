package com.sambat.demo.Dto.User;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private Integer age;
    private String password;
    private String address;
    private String role;
    private String email;
}
