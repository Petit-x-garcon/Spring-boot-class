package com.sambat.demo.Mapper;

import com.sambat.demo.Dto.Stock.StockDto;
import com.sambat.demo.Dto.Stock.StockResponseDto;
import com.sambat.demo.Entity.StockEntity;
import com.sambat.demo.Model.BaseResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper {
    public StockResponseDto stockEntityToDto(StockEntity stockEntity){
        StockResponseDto stockResponseDto = new StockResponseDto();

        stockResponseDto.setId(stockEntity.getId());
        stockResponseDto.setProductId(stockEntity.getProductId());
        stockResponseDto.setQuantity(stockEntity.getQuantity());
        stockResponseDto.setCreatedAt(stockEntity.getCreatedAt());
        stockResponseDto.setUpdatedAt(stockEntity.getUpdatedAt());

        return stockResponseDto;
    }

    public List<StockResponseDto> stockEntityToDtoList(List<StockEntity> stockEntities){
        return stockEntities.stream().map(this::stockEntityToDto).collect(Collectors.toList());
    }

    public StockEntity stockDtoToEntity(StockDto stockDto){
        StockEntity stockEntity = new StockEntity();

        stockEntity.setProductId(stockDto.getProductId());
        stockEntity.setQuantity(stockDto.getQuantity());

        return stockEntity;
    }

    public boolean updateStockEntityFromDto(StockEntity stockEntity, Long quantity, Integer type){

        if (type == 1){
            stockEntity.setQuantity(stockEntity.getQuantity() + quantity);
            return true;
        }
        else if(type == 2){
            if (stockEntity.getQuantity() < quantity){
                return false;
            }
            stockEntity.setQuantity(stockEntity.getQuantity() - quantity);
            return true;
        }
        return false;
    }
}
