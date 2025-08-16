package com.sambat.demo.Service;

import com.sambat.demo.Dto.Order.OrderItemDto;
import com.sambat.demo.Entity.StockEntity;
import com.sambat.demo.Exception.Model.UnprocessEntityException;
import com.sambat.demo.Repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StockManagementService {
    @Autowired
    private StockRepository stockRepository;

    @Transactional
    public void createOrder(List<OrderItemDto> orderItemDtos){
        List<Long> productId = orderItemDtos.stream()
                .map(OrderItemDto::getProductId).toList();

        List<StockEntity> stocks = stockRepository.findByProductIdIn(productId, Sort.by(Sort.Direction.ASC, "createdAt"));

        Map<Long, Integer> items = orderItemDtos.stream()
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
                throw new UnprocessEntityException("not enough stock for id" + id);
            }
        }

        stockRepository.saveAll(stocks);
    }
}
