package com.sambat.demo.Controller;

import com.sambat.demo.Dto.Order.OrderDto;
import com.sambat.demo.Dto.Order.OrderUpdateDto;
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

    @PatchMapping("{/id}")
    public ResponseEntity<BaseResponseModel> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderUpdateDto payload){
        return orderService.updateOrder(id, payload);
    }

    @DeleteMapping("{/id}")
    public  ResponseEntity<BaseResponseModel> deleteOrder(@PathVariable Long id){
        return orderService.deleteOrder(id);
    }
}
