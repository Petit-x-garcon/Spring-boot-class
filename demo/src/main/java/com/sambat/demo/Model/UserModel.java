package com.sambat.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String name;
    private Integer age;
    private String address;
    private String role;
    private String email;
}
