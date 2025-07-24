package com.sambat.demo.Mapper;

import com.sambat.demo.Dto.Product.ProductResponseDto;
import com.sambat.demo.Dto.Product.ProductDto;
import com.sambat.demo.Dto.Product.ProductResponseDto;
import com.sambat.demo.Entity.ProductEntity;
import com.sambat.demo.Entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductResponseDto productEntityToDto(ProductEntity productEntity){
        ProductResponseDto productResponseDto = new ProductResponseDto();

        productResponseDto.setId(productEntity.getId());
        productResponseDto.setName(productEntity.getProductName());
        productResponseDto.setDescription(productEntity.getDescription());
        productResponseDto.setPrice(productEntity.getPrice());
        productResponseDto.setCreatedAt(productEntity.getCreatedAt());
        productResponseDto.setUpdatedAt(productEntity.getUpdatedAt());

        return productResponseDto;
    }

    public List<ProductResponseDto> productEntityToDtoList(List<ProductEntity> productEntities){
        return productEntities.stream().map(this::productEntityToDto).collect(Collectors.toList());
    }

    public ProductEntity productDtoToEntity(ProductDto productDto){
        ProductEntity productEntity = new ProductEntity();

        productEntity.setProductName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setPrice(productDto.getPrice());

        return productEntity;
    }

    public void updateProductEntityFromDto(ProductEntity productEntity, ProductDto productDto){

        productEntity.setProductName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setPrice(productDto.getPrice());
    }
}
