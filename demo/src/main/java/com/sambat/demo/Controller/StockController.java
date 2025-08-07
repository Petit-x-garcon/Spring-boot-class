package com.sambat.demo.Controller;

import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Dto.Stock.StockDto;
import com.sambat.demo.Service.StockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping()
    public ResponseEntity<BaseDataResponseModel> getStroke(){
        return stockService.getStock();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDataResponseModel> getStockById(@PathVariable Long id){
        return stockService.getStockById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteStock(@PathVariable Long id){
        return stockService.deleteStock(id);
    }

    @PostMapping()
    public ResponseEntity<BaseResponseModel> addStock(@Valid @RequestBody StockDto payload){
        return stockService.addStock(payload);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateStock(@PathVariable Long id, @RequestBody Long quantity, @RequestParam(required = true) Integer type){
        return stockService.updateStock(id, quantity, type);
    }
}
