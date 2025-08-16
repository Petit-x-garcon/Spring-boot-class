package com.sambat.demo.Controller;

import com.sambat.demo.Dto.Base.Response;
import com.sambat.demo.Dto.Order.OrderDto;
import com.sambat.demo.Dto.Order.OrderResponseDto;
import com.sambat.demo.Dto.Order.OrderUpdateDto;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping()
    public ResponseEntity<Response> createOrder(@Valid @RequestBody OrderDto payload){

        orderService.createOrder(payload);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.success("success", "created order"));
    }

    @GetMapping
    public ResponseEntity<Response> getAllOrder(){
        List<OrderResponseDto> orderResponseDtos = orderService.getAllOrder();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "all orders", orderResponseDtos));
    }

    @PatchMapping("{/id}")
    public ResponseEntity<Response> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderUpdateDto payload){
        orderService.updateOrder(id, payload);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("success", "updated data"));
    }

    @DeleteMapping("{/id}")
    public  ResponseEntity<Response> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("success", "deleted data"));
    }
}
