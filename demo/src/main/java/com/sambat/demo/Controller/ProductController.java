package com.sambat.demo.Controller;

import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Model.ProductModel;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<BaseDataResponseModel> getProducts(){
        return productService.getProducts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseDataResponseModel> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    @PostMapping()
    public ResponseEntity<BaseResponseModel> addProduct(@RequestBody ProductModel payload){
        return productService.addProduct(payload);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateProductById(@PathVariable Long id, ProductModel payload){
        return productService.updateProductById(id, payload);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteProductById(@PathVariable Long id){
        return productService.deleteProductById(id);
    }
    @GetMapping("/search")
    public ResponseEntity<BaseDataResponseModel> searchProduct(@RequestParam(value = "name") String name) {
        return productService.searchProduct(name);
    }
}
