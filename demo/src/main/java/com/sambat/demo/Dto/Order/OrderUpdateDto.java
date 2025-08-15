package com.sambat.demo.Dto.Order;

import com.sambat.demo.Common.Annotations.ValidEnum;
import com.sambat.demo.Common.Enums.OrderStatus;
import lombok.Data;

@Data
public class OrderUpdateDto {
    @ValidEnum(enumClass = OrderStatus.class, message = "status must be in pending, fail, success")
    private String status;
}
