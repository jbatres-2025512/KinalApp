package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.Sale;

import java.util.List;
import java.util.Optional;

public interface ISaleService {

    //Metodo para listar ventas
    List<Sale>listAll();

    //Metodo para guardar ventas en la base de datos
    Sale save(Sale sale);

    //Metodo para buscar por codigo de venta
    Optional<Sale> findBySaleCode(Long saleCode);

    //Metodo para buscar por estado de venta
    List<Sale>findBySaleState();

    //Metodo para actualizar por codigo de venta
    Sale update(Long saleCode, Sale sale);

    //Metodo para elimiinar por codigo de venta
    void delete(Long saleCode);

    //Metodo para validar que exista la venta con su codigo
    boolean   existBySaleCode(Long saleCode);
    
}
