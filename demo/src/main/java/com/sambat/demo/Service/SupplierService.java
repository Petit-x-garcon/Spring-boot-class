package com.sambat.demo.Service;

import com.sambat.demo.Dto.Supplier.SupplierDto;
import com.sambat.demo.Dto.Supplier.SupplierResponseDto;
import com.sambat.demo.Entity.SupplierEntity;
import com.sambat.demo.Exception.Model.DuplicatedException;
import com.sambat.demo.Exception.Model.NotFoundHandler;
import com.sambat.demo.Mapper.SupplierMapper;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;

    public ResponseEntity<BaseDataResponseModel> getSupplier(){

        List<SupplierEntity> supplierEntities = supplierRepository.findAll();
        List<SupplierResponseDto> supplierResponseDtos = supplierMapper.supplierEntityToDtoList(supplierEntities);

        return ResponseEntity.ok(new BaseDataResponseModel("success", "All Suppliers", supplierResponseDtos));
    }

    public ResponseEntity<BaseDataResponseModel> getSupplierById(Long id){
        SupplierEntity supplierOpt = supplierRepository.findById(id).orElseThrow(()-> new NotFoundHandler("supplier not found" + id));

        SupplierResponseDto supplierResponseDto = supplierMapper.supplierEntityToDto(supplierOpt);
        return ResponseEntity.ok(new BaseDataResponseModel("success", "supplier found", supplierResponseDto));
    }

    public ResponseEntity<BaseResponseModel> addSupplier(SupplierDto payload){
        if(supplierRepository.existsByName(payload.getName())){
            throw new DuplicatedException("supplier already existed");
        }
        SupplierEntity supplierEntity = supplierMapper.supplierDtoToEntity(payload);

        supplierRepository.save(supplierEntity);

        return ResponseEntity.status(201).body(new BaseResponseModel("success", "supplier added"));
    }

    public ResponseEntity<BaseResponseModel> updateSupplierById(Long id, SupplierDto payload){
        SupplierEntity supplier = supplierRepository.findById(id).orElseThrow(() -> new NotFoundHandler("supplier not found " + id));

        supplierMapper.updateSupplierEntityFromDto(supplier, payload);
        supplierRepository.save(supplier);

        return ResponseEntity.ok(new BaseResponseModel("success", "supplier updated"));
    }

    public ResponseEntity<BaseResponseModel> deleteSupplierById(Long id){
        if(!supplierRepository.existsById(id)){
           throw new NotFoundHandler("supplier not found " + id);
        }
        supplierRepository.deleteById(id);
        return ResponseEntity.ok(new BaseResponseModel("success", "supplier deleted"));
    }
}
