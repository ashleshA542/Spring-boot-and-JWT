package com.jwt.project.controller;

import com.jwt.project.dto.ProductDto;
import com.jwt.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping({"/api/products"})
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping({"/create/{supplierId}"})
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product, @PathVariable int supplierId ) {
        ProductDto createProduct=productService.createProduct(product,supplierId);
        return new ResponseEntity<ProductDto>(createProduct, HttpStatus.CREATED) ;
    }

    @GetMapping({"/viewAll"})
    public ResponseEntity<List<ProductDto>> viewAllProducts() {
        List<ProductDto> viewAll = productService.viewAllProducts();
        return new ResponseEntity<List<ProductDto>>(viewAll,HttpStatus.ACCEPTED);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> viewProductById(@PathVariable int productId) {
        ProductDto viewById= productService.viewProductById(productId);
        return new ResponseEntity<ProductDto>(viewById,HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<String>("Product deleted successfully.",HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable int productId, @RequestBody ProductDto updatedProduct) {
        ProductDto updateProduct = productService.updateProduct(productId, updatedProduct);
        return new ResponseEntity<ProductDto>(updateProduct,HttpStatus.ACCEPTED);

    }


    //Find product by supplier wise

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<ProductDto>> getProductBySuplier(@PathVariable int supplierId ){

        List<ProductDto> findProductBySupplier=productService.findProductBySupplier(supplierId);

        return new ResponseEntity<List<ProductDto>>(findProductBySupplier,HttpStatus.ACCEPTED);
    }


}

