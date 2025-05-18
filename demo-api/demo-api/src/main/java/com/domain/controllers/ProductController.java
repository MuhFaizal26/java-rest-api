package com.domain.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.dto.ResponseData;
import com.domain.dto.SearchDto;
import com.domain.models.entities.Product;
import com.domain.models.entities.Supplier;
import com.domain.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Create a new product
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseData<Product>> create(@Valid @RequestBody Product product, Errors errors) {
        ResponseData<Product> responseData = new ResponseData<>();
        responseData.setMessage(new ArrayList<>());

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }

    // Update an existing product
    @PutMapping(consumes = "application/json")
    public ResponseEntity<ResponseData<Product>> update(@Valid @RequestBody Product product, Errors errors) {
        ResponseData<Product> responseData = new ResponseData<>();
        responseData.setMessage(new ArrayList<>());

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }

    // Get all products
    @GetMapping
    public Iterable<Product> findAll() {
        return productService.findAll();
    }

    // Get a product by ID
    @GetMapping("/{id}")
    public Product findOne(@PathVariable Long id) {
        return productService.findOne(id);
    }

    // Delete a product by ID
    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable Long id) {
        productService.removeOne(id);
    }

    // Add a supplier to a product
    @PostMapping(value = "/{id}", consumes = "application/json")
    public void addSupplier(@RequestBody Supplier supplier, @PathVariable("id") long productId) {
        productService.addSupplier(supplier, productId);
    }

    // Search for a product by exact name
    @PostMapping(value = "/search/name", consumes = "application/json")
    public Product getProductByName(@RequestBody SearchDto searchDto) {
        return productService.findByProductName(searchDto.getSearchKey());
    }

    // Search for products by name pattern
    @PostMapping(value = "/search/namelike", consumes = "application/json")
    public List<Product> getProductByNameLike(@RequestBody SearchDto searchDto) {
        return productService.findByProductNameLike(searchDto.getSearchKey());
    }

    // Get products by category ID
    @GetMapping("/search/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("categoryId") Long categoryId) {
        List<Product> products = productService.findByCategory(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(products);
        }
        return ResponseEntity.ok(products);
    }

    // Get products by supplier ID
    @GetMapping("/search/supplier/{supplierId}")
    public List<Product> getProductBySupplier(@PathVariable("supplierId") Long supplierId) {
        return productService.findBySupplier(supplierId);
    }
}
