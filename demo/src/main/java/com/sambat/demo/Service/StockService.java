package com.sambat.demo.Service;

import com.sambat.demo.Dto.Stock.StockResponseDto;
import com.sambat.demo.Entity.ProductEntity;
import com.sambat.demo.Entity.StockEntity;
import com.sambat.demo.Exception.Model.NotFoundHandler;
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
        StockEntity existStock = stockRepository.findById(id).orElseThrow(() -> new NotFoundHandler("not found id " + id));
        StockResponseDto stockResponseDto = stockMapper.stockEntityToDto(existStock);

        return ResponseEntity.ok(new BaseDataResponseModel("success", "product found", stockResponseDto));
    }

    public ResponseEntity<BaseResponseModel> deleteStock(Long id){
        if(!stockRepository.existsById(id)){
            throw new NotFoundHandler("stock doesn't exist" + id);
        }
        stockRepository.deleteById(id);
        return ResponseEntity.ok(new BaseResponseModel("success", "product deleted"));
    }

    public ResponseEntity<BaseResponseModel> addStock(StockDto payload) {
        ProductEntity stockOpt = productRepository.findById(payload.getProductId()).orElseThrow(() -> new NotFoundHandler("product not found"));

        StockEntity stock = stockMapper.stockDtoToEntity(payload, stockOpt);
        stockRepository.save(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseModel("success", "stock added"));
    }

    public ResponseEntity<BaseResponseModel> updateStock(Long id, Long quantity, Integer type){
        if(type != 1 && type != 2){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponseModel("fail", "type must be 1 or 2"));
        }

        StockEntity existStock = stockRepository.findById(id).orElseThrow(() -> new NotFoundHandler("stock not found"));

        boolean status = stockMapper.updateStockEntityFromDto(existStock, quantity, type);
        if(!status){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponseModel("fail", "quantity must be greater than 0"));
        }
        stockRepository.save(existStock);
        return ResponseEntity.ok(new BaseResponseModel("success", "stock updated"));
    }
}
