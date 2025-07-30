package com.sambat.demo.Model;

import com.sambat.demo.Entity.ProductEntity;
import lombok.Data;

import java.util.List;
import java.util.Objects;
@Data
public class BaseDataResponseModel extends BaseResponseModel{
    private Object Data;

    public BaseDataResponseModel(String status, String message, Object Data) {
        super(status, message);
        this.Data = Data;
    }
}
