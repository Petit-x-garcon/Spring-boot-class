package com.sambat.demo.Service;

import com.sambat.demo.Dto.Order.OrderDto;
import com.sambat.demo.Dto.Order.OrderResponseDto;
import com.sambat.demo.Dto.Order.OrderUpdateDto;
import com.sambat.demo.Entity.OrderEntity;
import com.sambat.demo.Exception.Model.NotFoundHandler;
import com.sambat.demo.Mapper.OrderMapper;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Repository.OrderRespository;
import com.sambat.demo.Service.mail.NotificationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderRespository orderRespository;
    @Autowired
    private StockManagementService stockManagementService;
    @Autowired
    private NotificationService notificationService;

    public List<OrderResponseDto> getAllOrder(){
         List<OrderEntity> orderEntities = orderRespository.findAll();

         List<OrderResponseDto> dtos = orderMapper.toOrderDtoList(orderEntities);

        return dtos;
    }

    public void updateOrder(Long id, OrderUpdateDto payload){
        OrderEntity entity = orderRespository.findById(id).orElseThrow(() -> new NotFoundHandler("order not found"));

        orderMapper.updateOrder(entity, payload);
        orderRespository.save(entity);
    }

    public void deleteOrder(Long id){
        if(!orderRespository.existsById(id)){
            throw new NotFoundHandler("order doesn't exist");
        }
        orderRespository.deleteById(id);
    }

    @Transactional
    public void createOrder(OrderDto payload){
        String threadName    = Thread.currentThread().getName();
        log.info("[SYNC-ORDER] Creating new order | Thread: {}", threadName);

        stockManagementService.createOrder(payload.getOrderItemDtoList());

        OrderEntity orderEntity = orderMapper.orderDtoToEntity(payload);
        orderRespository.save(orderEntity);

        log.info("[SYNC-ORDER] Order created successfully with Order: {} | Thread: {}",orderEntity.getId(), threadName);
        log.info("[SYNC-ORDER] Trigger send notification asynchronously for Order: {} | Thread: {}",orderEntity.getId(), threadName );

        notificationService.sendMail(orderEntity);
        log.info("[SYNC-ORDER-COMPLETED] Completed order and triggered send notification");
    }
}
