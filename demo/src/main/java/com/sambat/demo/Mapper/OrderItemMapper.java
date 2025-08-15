package com.sambat.demo.Mapper;

import com.sambat.demo.Dto.Order.OrderItemDto;
import com.sambat.demo.Dto.Order.OrderItemResponseDto;
import com.sambat.demo.Entity.OrderItemEntity;
import com.sambat.demo.Entity.ProductEntity;
import com.sambat.demo.Exception.Model.NotFoundHandler;
import com.sambat.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemMapper {
    @Autowired
    private ProductRepository productRepository;

    public OrderItemEntity orderItemDtoToEntity(OrderItemDto orderItemDto){
        OrderItemEntity orderItemEntity = new OrderItemEntity();

        orderItemEntity.setQuantity(orderItemDto.getQuantity());
        orderItemEntity.setProductId(orderItemDto.getProductId());

        return orderItemEntity;
    }

    public OrderItemResponseDto toItemDto(OrderItemEntity entity){
        if(entity == null) return null;

        OrderItemResponseDto dto = new OrderItemResponseDto();

        dto.setProductId(entity.getProductId());
        dto.setPurchaseAmount(entity.getQuantity());

        ProductEntity product = productRepository.findById(entity.getProductId())
                .orElseThrow(() -> new NotFoundHandler("product not found"));

        dto.setProductName(product.getProductName());
        dto.setUnitPrice(product.getPrice());

        return dto;
    }

    public List<OrderItemResponseDto> toItemDtoList(List<OrderItemEntity> entities){
        return entities.stream().map(this::toItemDto).toList();
    }
}
