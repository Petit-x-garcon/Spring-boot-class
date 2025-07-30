package com.sambat.demo.Service;

import com.sambat.demo.Dto.Stock.StockResponseDto;
import com.sambat.demo.Entity.ProductEntity;
import com.sambat.demo.Entity.StockEntity;
import com.sambat.demo.Mapper.StockMapper;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Dto.Stock.StockDto;
import com.sambat.demo.Repository.ProductRepository;
import com.sambat.demo.Repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<BaseDataResponseModel> getStock(){
        List<StockEntity> stock = stockRepository.findAll();
        List<StockResponseDto> stockResponseDto = stockMapper.stockEntityToDtoList(stock);
        return ResponseEntity.ok(new BaseDataResponseModel("success", "all stocks", stockResponseDto));
    }

    public ResponseEntity<BaseDataResponseModel> getStockById(Long id) {
        Optional<StockEntity> existStock = stockRepository.findById(id);
        return existStock.map(stockDto -> {
            StockResponseDto stockResponseDto = stockMapper.stockEntityToDto(stockDto);
            return ResponseEntity.ok(new BaseDataResponseModel("success", "stock found", stockResponseDto));
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseDataResponseModel("fail", "stock not found!", null)));
    }

    public ResponseEntity<BaseResponseModel> deleteStock(Long id){
        Optional<StockEntity> existStock = stockRepository.findById(id);
        return existStock.map(stockEntity -> {
            stockRepository.delete(stockEntity);
            return ResponseEntity.ok(new BaseResponseModel("success", "stock deleted"));
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponseModel("fail", "stock not found")));
    }

    public ResponseEntity<BaseResponseModel> addStock(StockDto payload) {
        Optional<ProductEntity> stockOpt = productRepository.findById(payload.getProductId());
        if(stockOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponseModel("fail", "product not found"));
        }
        StockEntity stock = stockMapper.stockDtoToEntity(payload, stockOpt.get());
        stockRepository.save(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseModel("success", "stock added"));
    }

    public ResponseEntity<BaseResponseModel> updateStock(Long id, Long quantity, Integer type){
        if(type != 1 && type != 2){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponseModel("fail", "type must be 1 or 2"));
        }

        Optional<StockEntity> existStock = stockRepository.findById(id);
        if(existStock.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponseModel("fail", "stock not found"));
        }

        boolean status = stockMapper.updateStockEntityFromDto(existStock.get(), quantity, type);
        if(!status){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponseModel("fail", "quantity must be greater than 0"));
        }
        stockRepository.save(existStock.get());
        return ResponseEntity.ok(new BaseResponseModel("success", "stock updated"));
    }
}
