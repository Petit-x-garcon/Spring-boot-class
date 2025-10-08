package com.sambat.demo.Controller;

import com.sambat.demo.Dto.Base.PaginationResponse;
import com.sambat.demo.Dto.Base.Response;
import com.sambat.demo.Dto.Product.ProductResponseDto;
import com.sambat.demo.Model.BaseResponseModel;
import com.sambat.demo.Dto.Product.ProductDto;
import com.sambat.demo.Model.BaseDataResponseModel;
import com.sambat.demo.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/pagination")
    public ResponseEntity<Response> listProductPaganation(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        PaginationResponse<ProductResponseDto> products = productService.listProductPageable(pageable);
        return ResponseEntity.ok(Response.success("200", "success",
                "10 pageanations items", products));
    }

    @GetMapping()
    public ResponseEntity<BaseDataResponseModel> getProducts(){
        return productService.getProducts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseDataResponseModel> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    @PostMapping()
    public ResponseEntity<BaseResponseModel> addProduct(@Valid @RequestBody ProductDto payload){
        return productService.addProduct(payload);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateProductById(@PathVariable Long id, @RequestBody ProductDto payload){
        return productService.updateProductById(id, payload);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteProductById(@PathVariable Long id){
        return productService.deleteProductById(id);
    }
    @GetMapping("/search")
    public ResponseEntity<BaseDataResponseModel> searchProduct(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "min", required = false) Double minPrice,
            @RequestParam(value = "max", required = false) Double maxPrice) {
        return productService.searchProduct(name, minPrice, maxPrice);
    }
}
