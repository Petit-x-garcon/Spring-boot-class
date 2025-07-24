package com.sambat.demo.Model;

import lombok.*;

@Data
@NoArgsConstructor
public class BaseResponseModel {
    private String status;
    private String message;

    public BaseResponseModel(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
