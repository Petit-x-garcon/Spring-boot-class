package com.sambat.demo.Service;

import com.sambat.demo.Dto.Order.OrderDto;
import com.sambat.demo.Dto.Order.OrderItemDto;
import com.sambat.demo.Dto.Order.OrderResponseDto;
import com.sambat.demo.Entity.OrderEntity;
import com.sambat.demo.Entity.StockEntity;
import com.sambat.demo.Mapper.OrderMapper;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Repository.OrderRespository;
import com.sambat.demo.Repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderRespository orderRespository;
    @Autowired
    private StockRepository stockRepository;

    public ResponseEntity<BaseDataResponseModel> getAllOrder(){
         List<OrderEntity> orderEntities = orderRespository.findAll();

         List<OrderResponseDto> orderResponseDtos = orderMapper.toOrderDtoList(orderEntities);

        return ResponseEntity.ok(new BaseDataResponseModel("success", "here is  all your order", orderResponseDtos));
    }

    @Transactional
    public ResponseEntity<BaseResponseModel> createOrder(OrderDto payload){

        List<Long> productId = payload.getOrderItemDtoList().stream()
                .map(OrderItemDto::getProductId).toList();

        List<StockEntity> stocks = stockRepository.findByProductIdIn(productId, Sort.by(Sort.Direction.ASC, "createdAt"));

        Map<Long, Integer> items = payload.getOrderItemDtoList().stream()
                .collect(Collectors.toMap(OrderItemDto::getProductId, OrderItemDto::getQuantity));

        for(Long id : items.keySet()){
            Long toDeduct = items.get(id).longValue();

            List<StockEntity> stocksByProductId = stocks.stream()
                    .filter(stock -> stock.getProduct().getId().equals(id)).toList();

            for(StockEntity stock : stocksByProductId){
                if(toDeduct <= 0) break;

                Long available = stock.getQuantity();

                if(available >= toDeduct){
                    stock.setQuantity(available - toDeduct);
                    toDeduct = 0L;
                }
                else{
                    stock.setQuantity(0L);
                    toDeduct -= available;
                }
            }
            if(toDeduct > 0){
                throw new RuntimeException("not enough stock for id" + id);
            }
        }

        stockRepository.saveAll(stocks);

        OrderEntity orderEntity = orderMapper.orderDtoToEntity(payload);
        orderRespository.save(orderEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseModel("success", "created order"));
    }
}
