package com.sambat.demo.Service;

import com.sambat.demo.Entity.ProductEntity;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Model.ProductModel;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<BaseDataResponseModel> getProducts(){
        List<ProductEntity> products = productRepository.findAll();
        return ResponseEntity.ok(new BaseDataResponseModel("success", "All Products", products));
    }

    public ResponseEntity<BaseDataResponseModel> getProductById(Long id){
        Optional<ProductEntity> productOpt = productRepository.findById(id);

        if(productOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseDataResponseModel("fail", "product not found", List.of()));
        }

        ProductEntity product = productOpt.get();
        return ResponseEntity.ok(new BaseDataResponseModel("success", "product found", product));
    }

    public ResponseEntity<BaseResponseModel> addProduct(ProductModel payload){
        ProductEntity product = new ProductEntity();

        product.setProductName(payload.getName());
        product.setDescription(payload.getDescription());
        product.setPrice(payload.getPrice());
        product.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseModel("success", "product added"));
    }

    public ResponseEntity<BaseResponseModel> updateProductById(Long id, ProductModel payload){
        Optional<ProductEntity> productOpt = productRepository.findById(id);
        if(productOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponseModel("fail", "product not found"));
        }
        ProductEntity product = productOpt.get();
        product.setProductName(payload.getName());
        product.setPrice(payload.getPrice());
        product.setDescription(payload.getDescription());
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);
        return ResponseEntity.ok(new BaseResponseModel("success", "product updated"));
    }

    public ResponseEntity<BaseResponseModel> deleteProductById(Long id) {
        Optional<ProductEntity> productOpt = productRepository.findById(id);

        if(productOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponseModel("fail", "product not found"));
        }

        productRepository.deleteById(id);
        return ResponseEntity.ok(new BaseResponseModel("success", "product deleted"));
    }

    public ResponseEntity<BaseDataResponseModel> searchProduct(String name){
        String nameCheck = name != null ? name.toLowerCase() : null;
        ProductEntity product = productRepository.findByProductName(nameCheck);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseDataResponseModel("success", "product found", product));
    }
}
