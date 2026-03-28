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

    @GetMapping("/{productCode}")
    public ResponseEntity<Product>searchByProductCode(@PathVariable Long productCode){

        return productService.findProductByCode(productCode)

                .map(ResponseEntity::ok)

                .orElse(ResponseEntity.notFound().build());


    }

    @GetMapping("/actives")
    public ResponseEntity<List<Product>>findByProductState(){
        return ResponseEntity.ok(productService.findByProductState());
    }

    @PostMapping
    public ResponseEntity<?>save(@RequestBody Product product){

        try {

            Product newProduct = productService.save(product);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);



        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @DeleteMapping("/{productCode}")
    public  ResponseEntity<Void>delete(@PathVariable Long productCode){

        try {

            if (!productService.existByProductCode(productCode)){
                return ResponseEntity.notFound().build();
            }

            productService.delete(productCode);
            return ResponseEntity.noContent().build();

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{productCode}")
    public ResponseEntity<?> update(@PathVariable Long productCode, @RequestBody Product product){

        try {

            if (!productService.existByProductCode(productCode)){
                return ResponseEntity.notFound().build();
            }

            Product updateProduct = productService.update(productCode,product);

            return ResponseEntity.ok(updateProduct);


        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }

    }

}
