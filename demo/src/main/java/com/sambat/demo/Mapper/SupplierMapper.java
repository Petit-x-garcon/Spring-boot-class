package com.sambat.demo.Mapper;

import com.sambat.demo.Dto.Supplier.SupplierDto;
import com.sambat.demo.Dto.Supplier.SupplierResponseDto;
import com.sambat.demo.Entity.SupplierEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class SupplierMapper {
    public SupplierEntity supplierDtoToEntity(SupplierDto supplierDto){
        SupplierEntity supplierEntity = new SupplierEntity();

        supplierEntity.setName(supplierDto.getName());
        supplierEntity.setAddress(supplierDto.getAddress());
        supplierEntity.setRating(supplierDto.getRating());
        supplierEntity.setEmail(supplierDto.getEmail());
        supplierEntity.setPhone(supplierDto.getPhone());

        return supplierEntity;
    }

    public SupplierResponseDto supplierEntityToDto(SupplierEntity supplierEntity){
        SupplierResponseDto supplierResponseDto = new SupplierResponseDto();

        supplierResponseDto.setId(supplierEntity.getId());
        supplierResponseDto.setName(supplierEntity.getName());
        supplierResponseDto.setAddress(supplierEntity.getAddress());
        supplierResponseDto.setRating(supplierEntity.getRating());
        supplierResponseDto.setEmail(supplierEntity.getEmail());
        supplierResponseDto.setPhone(supplierEntity.getPhone());
        supplierResponseDto.setCreatedAt(supplierEntity.getCreatedAt());
        supplierResponseDto.setUpdatedAt(supplierEntity.getUpdatedAt());

        return supplierResponseDto;
    }

    public List<SupplierResponseDto> supplierEntityToDtoList(List<SupplierEntity> supplierEntities){
        return supplierEntities.stream().map(this::supplierEntityToDto).collect(Collectors.toList());
    }

    public void updateSupplierEntityFromDto(SupplierEntity supplierEntity, SupplierDto supplierDto){
        supplierEntity.setName(supplierDto.getName());
        supplierEntity.setAddress(supplierDto.getAddress());
        supplierEntity.setRating(supplierDto.getRating());
        supplierEntity.setEmail(supplierDto.getEmail());
        supplierEntity.setPhone(supplierDto.getPhone());
    }
}
