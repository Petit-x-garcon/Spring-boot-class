package com.sambat.demo.Service;

import com.sambat.demo.Common.config.ApplicationConfiguration;
import com.sambat.demo.Dto.Base.PaginationResponse;
import com.sambat.demo.Dto.Product.ProductResponseDto;
import com.sambat.demo.Entity.ProductEntity;
import com.sambat.demo.Exception.Model.DuplicatedException;
import com.sambat.demo.Exception.Model.NotFoundHandler;
import com.sambat.demo.Mapper.ProductMapper;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Dto.Product.ProductDto;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ApplicationConfiguration appConfig;


    public PaginationResponse<ProductResponseDto> listProductPageable(Pageable pageable){
        Page<ProductEntity> products = productRepository.findAll(pageable);
        Page<ProductResponseDto> responseDtos = products.map(product
                -> productMapper.productEntityToDto(product));
        return PaginationResponse.from(responseDtos, appConfig.getPagination().buildUrl("product"));
    }

    @Cacheable(value = "products", key = "'all'")
    public List<ProductResponseDto> getProducts(){
        List<ProductEntity> products = productRepository.findAll();
        return productMapper.productEntityToDtoList(products);
    }

    public ResponseEntity<BaseDataResponseModel> getProductById(Long id){
        ProductEntity productOpt = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundHandler("not found with id " + id));

        ProductResponseDto productResponseDto = productMapper.productEntityToDto(productOpt);
        return ResponseEntity.ok(new BaseDataResponseModel("success", "product found", productResponseDto));
    }

    public ResponseEntity<BaseResponseModel> addProduct(ProductDto payload){
        if(productRepository.existsByProductName(payload.getName())){
            throw new DuplicatedException("product with this name is already existed");
        }
        ProductEntity product = productMapper.productDtoToEntity(payload);
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponseModel("success", "product added"));
    }

    public ResponseEntity<BaseResponseModel> updateProductById(Long id, ProductDto payload){
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new NotFoundHandler("not found with id " + id));

        productMapper.updateProductEntityFromDto(product, payload);
        productRepository.save(product);
        return ResponseEntity.ok(new BaseResponseModel("success", "product updated"));
    }

    public ResponseEntity<BaseResponseModel> deleteProductById(Long id) {
        if(!productRepository.existsById(id)){
            throw new NotFoundHandler("not found by id " + id);
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
