package com.sambat.demo.Mapper;

import com.sambat.demo.Dto.Order.OrderDto;
import com.sambat.demo.Dto.Order.OrderItemResponseDto;
import com.sambat.demo.Dto.Order.OrderResponseDto;
import com.sambat.demo.Entity.OrderEntity;
import com.sambat.demo.Entity.OrderItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    @Autowired
    private OrderItemMapper orderItemMapper;

    public OrderEntity orderDtoToEntity(OrderDto orderDtos){
        OrderEntity orderEntity = new OrderEntity();

        List<OrderItemEntity> orderItemEntities = orderDtos.getOrderItemDtoList().stream()
                .map(orderItemDto -> {
                    OrderItemEntity orderItemEntity = orderItemMapper.orderItemDtoToEntity(orderItemDto);
                    orderItemEntity.setOrder(orderEntity);
                    return orderItemEntity;
                }).toList();

        orderEntity.setOrderItems(orderItemEntities);
        return orderEntity;
    }

    public OrderResponseDto toOrderDto(OrderEntity entity){
        if(entity == null) return null;

        OrderResponseDto orderResponseDto = new OrderResponseDto();

        orderResponseDto.setId(entity.getId());
        orderResponseDto.setStatus(entity.getStatus());
        orderResponseDto.setCreatedAt(entity.getCreatedAt());
        orderResponseDto.setUpdatedAt(entity.getUpdatedAt());

        if(entity.getOrderItems() != null && !entity.getOrderItems().isEmpty()){
            List<OrderItemResponseDto> orderItemResponseDtos = orderItemMapper.toItemDtoList(entity.getOrderItems());

            orderResponseDto.setItems(orderItemResponseDtos);

            Double total = orderItemResponseDtos.stream().mapToDouble(item ->
                    item.getPurchaseAmount() * item.getUnitPrice()).sum();

            orderResponseDto.setTotal(total);
        }

        return orderResponseDto;
    }

    public List<OrderResponseDto> toOrderDtoList(List<OrderEntity> entities){
        return entities.stream().map(this::toOrderDto).toList();
    }
}
