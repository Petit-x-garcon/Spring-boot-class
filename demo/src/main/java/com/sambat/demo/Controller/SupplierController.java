package com.sambat.demo.Controller;

import com.sambat.demo.Dto.Supplier.SupplierDto;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/suppliers")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping()
    public ResponseEntity<BaseDataResponseModel> getSuppliers(){
        return supplierService.getSupplier();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDataResponseModel> getSupplierById(@PathVariable Long id){
        return supplierService.getSupplierById(id);
    }

    @PostMapping()
    public ResponseEntity<BaseResponseModel> addSupplier(@RequestBody SupplierDto payload){
        return supplierService.addSupplier(payload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateSupplierById(@PathVariable Long id, @RequestBody SupplierDto payload){
        return supplierService.updateSupplierById(id, payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteSupplierById(@PathVariable Long id){
        return supplierService.deleteSupplierById(id);
    }
}
