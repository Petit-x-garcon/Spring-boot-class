package com.sambat.demo.Service;

import com.sambat.demo.Dto.Supplier.SupplierDto;
import com.sambat.demo.Dto.Supplier.SupplierResponseDto;
import com.sambat.demo.Entity.SupplierEntity;
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
        Optional<SupplierEntity> supplierOpt = supplierRepository.findById(id);

        if(supplierOpt.isEmpty()){
            return ResponseEntity.status(404).body(new BaseDataResponseModel("fail", "supplier not found", null));
        }

        SupplierResponseDto supplierResponseDto = supplierMapper.supplierEntityToDto(supplierOpt.get());
        return ResponseEntity.ok(new BaseDataResponseModel("success", "supplier found", supplierResponseDto));
    }

    public ResponseEntity<BaseResponseModel> addSupplier(SupplierDto payload){
        if(supplierRepository.existsByName(payload.getName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponseModel("fail", "supplier alreadu exited" + payload.getName()));
        }
        SupplierEntity supplierEntity = supplierMapper.supplierDtoToEntity(payload);

        supplierRepository.save(supplierEntity);

        return ResponseEntity.status(201).body(new BaseResponseModel("success", "supplier added"));
    }

    public ResponseEntity<BaseResponseModel> updateSupplierById(Long id, SupplierDto payload){
        Optional<SupplierEntity> supplierOpt = supplierRepository.findById(id);

        if(supplierOpt.isEmpty()){
            return ResponseEntity.status(404).body(new BaseResponseModel("fail", "supplier not found"));
        }
        SupplierEntity supplier = supplierOpt.get();
        supplierMapper.updateSupplierEntityFromDto(supplier, payload);
        supplierRepository.save(supplier);

        return ResponseEntity.ok(new BaseResponseModel("success", "supplier updated"));
    }

    public ResponseEntity<BaseResponseModel> deleteSupplierById(Long id){
        Optional<SupplierEntity> supplierOpt = supplierRepository.findById(id);
        if(supplierOpt.isEmpty()){
            return ResponseEntity.status(404).body(new BaseResponseModel("fail", "supplier not found"));
        }
        supplierRepository.delete(supplierOpt.get());
        return ResponseEntity.ok(new BaseResponseModel("success", "supplier deleted"));
    }
}
