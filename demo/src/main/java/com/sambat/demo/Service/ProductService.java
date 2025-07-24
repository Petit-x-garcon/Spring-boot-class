package com.sambat.demo.Service;

import com.sambat.demo.Dto.Product.ProductResponseDto;
import com.sambat.demo.Entity.ProductEntity;
import com.sambat.demo.Mapper.ProductMapper;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Dto.Product.ProductDto;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ResponseEntity<BaseDataResponseModel> getProducts(){
        List<ProductEntity> products = productRepository.findAll();
        List<ProductResponseDto> productResponseDto = productMapper.productEntityToDtoList(products);
        return ResponseEntity.ok(new BaseDataResponseModel("success", "All Products", productResponseDto));
    }

    public ResponseEntity<BaseDataResponseModel> getProductById(Long id){
        Optional<ProductEntity> productOpt = productRepository.findById(id);

        if(productOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseDataResponseModel("fail", "product not found", null));
        }

        ProductResponseDto productResponseDto = productMapper.productEntityToDto(productOpt.get());
        return ResponseEntity.ok(new BaseDataResponseModel("success", "product found", productResponseDto));
    }

    public ResponseEntity<BaseResponseModel> addProduct(ProductDto payload){
        if(productRepository.existsByProductName(payload.getName())){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponseModel("fail", "product already exists"));
        }
        ProductEntity product = productMapper.productDtoToEntity(payload);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseModel("success", "product added"));
    }

    public ResponseEntity<BaseResponseModel> updateProductById(Long id, ProductDto payload){
        Optional<ProductEntity> productOpt = productRepository.findById(id);
        if(productOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponseModel("fail", "product not found"));
        }
        ProductEntity product = productOpt.get();
        productMapper.updateProductEntityFromDto(product, payload);
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

    public ResponseEntity<BaseDataResponseModel> searchProduct(String name, Double minPrice, Double maxPrice){
        String nameCheck = name != null ? name.toLowerCase() : null;
        List<ProductEntity> product = productRepository.findByProductName(nameCheck, minPrice, maxPrice);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseDataResponseModel("success", "product found", product));
    }
}
