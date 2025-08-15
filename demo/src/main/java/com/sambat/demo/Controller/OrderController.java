package com.sambat.demo.Controller;

import com.sambat.demo.Dto.Order.OrderDto;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ResponseEntity<BaseResponseModel> createOrder(@Valid @RequestBody OrderDto payload){
        return orderService.createOrder(payload);
    }

    @GetMapping
    public ResponseEntity<BaseDataResponseModel> getAllOrder(){
        return orderService.getAllOrder();
    }
}
