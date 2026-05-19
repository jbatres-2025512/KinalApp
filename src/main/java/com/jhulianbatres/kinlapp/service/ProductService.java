package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.Product;
import com.jhulianbatres.kinlapp.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProductService implements IProductService{

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> listAll(){
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByProductState(){
        return productRepository.findAll().stream().filter(productState -> productState.getProductState() !=0).collect(Collectors.toList());
    }

    @Override
    public Product save(Product product){

        validateProduct(product);

        if (product.getProductState() == 0){
            product.setProductState(1);
        }

        return productRepository.save(product);
    }

    @Override
    public Optional<Product>findProductByCode(Long productCode){
        return productRepository.findById(productCode);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Product update(Long productCode , Product product){
        if (!productRepository.existsById(productCode)){
            throw new  RuntimeException("No se encontro ningun producto con el codigo: " + productCode);
        }

        product.setProductCode(productCode);

        validateProduct(product);

        return productRepository.save(product);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long productCode){

        if (!productRepository.existsById(productCode)){
            throw new RuntimeException("No se encontro ningun producto con el codigo: " + productCode);
        }

        productRepository.deleteById(productCode);


    }

    @Override
    @Transactional(readOnly = true)
    public boolean existByProductCode(Long productCode){
        return productRepository.existsById(productCode);
    }

    public void validateProduct(Product product){

        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        if (product.getPrice() == null) {
            throw new IllegalArgumentException("El precio es obligatorio");
        }

        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        if (product.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }


    }













}
