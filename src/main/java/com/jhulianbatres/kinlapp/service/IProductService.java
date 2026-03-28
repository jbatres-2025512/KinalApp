package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    //Metodo para listar todos  los productos
    List<Product>listAll();

    //Metodo para buscar un producto con su codigo de producto
    Optional<Product> findProductByCode(Long productCode);

    //Metodod para agregar  productos
    Product save(Product product);

    //Metodo para buscar por estado de venta
    List<Product>findByProductState();

    //Metodo para actualizar productos
    Product update(Long productCode,Product  product);

    //Metodo para eliminar productos utilizando su codigo de producto
    void delete(Long productCode);

    //Metodo para validar que exista un producto por medio de su codigo de producto
    boolean existByProductCode(Long productCode);



}
