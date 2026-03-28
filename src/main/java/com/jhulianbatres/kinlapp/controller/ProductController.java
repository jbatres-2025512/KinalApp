package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.entity.Product;
import com.jhulianbatres.kinlapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>>listAll(){

        List<Product> products = productService.listAll();

        return ResponseEntity.ok(products);
    }

}
