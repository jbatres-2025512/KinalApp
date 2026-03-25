package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.SaleDetail;

import java.util.List;
import java.util.Optional;

public interface ISaleDetail {

    //Metodo para listar todos los detalles de ventas
    List<SaleDetail> listAll();

    //Metodo para guardar los detalles de venta en la base de datos
    SaleDetail save(SaleDetail saleDetail);

    //Metodo para buscar por codigo de el detalle de venta
    Optional<SaleDetail> findBySaleDetailCode(Long codeSaleDetail);

    //Metodo para actualizar por codigo de detalle de venta
    SaleDetail update(Long codeSaleDetail, SaleDetail saleDetail);

    //Metodo para eliminar por id de detalle de venta
    void delete (Long codeSaleDetail);

    //Metodo para validar que exista por id de el detalle de venta
    boolean existByCodeSaleDetail(Long codeSaleDetail);


}
