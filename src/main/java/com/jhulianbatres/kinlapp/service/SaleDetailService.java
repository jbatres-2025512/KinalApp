package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.SaleDetail;
import com.jhulianbatres.kinlapp.repository.SaleDetailRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service

@Transactional

public class SaleDetailService implements ISaleDetailService {

    private final SaleDetailRepository saleDetailRepository;

    public SaleDetailService(SaleDetailRepository saleDetailRepository) {
        this.saleDetailRepository = saleDetailRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<SaleDetail> listAll() {
        return saleDetailRepository.findAll();
    }

    @Override
    public SaleDetail save(SaleDetail saleDetail) {

        validateSaleDetail(saleDetail);
        return saleDetailRepository.save(saleDetail);
    }

    @Override
    public Optional<SaleDetail> findBySaleDetailCode(Long codeSaleDetail) {

        if (!saleDetailRepository.existsById(codeSaleDetail))
            throw new RuntimeException("No se encontro ningun detalle de venta con el codigo: " + codeSaleDetail);

        return saleDetailRepository.findById(codeSaleDetail);
    }

    @Override
    public SaleDetail update(Long codeSaleDetail, SaleDetail saleDetail) {
        if (!saleDetailRepository.existsById(codeSaleDetail))
            throw new RuntimeException("No se encontro nigun detalle de venta para actualizar con el codigo: " + codeSaleDetail);
        saleDetail.setCodeSaleDetail(codeSaleDetail);

        validateSaleDetail(saleDetail);

        return saleDetailRepository.save(saleDetail);

    }

    @Override
    public void delete(Long codeSaleDetail) {
        if (!saleDetailRepository.existsById(codeSaleDetail)) {
            throw new RuntimeException("No se encontro nigun detalle de venta para eliminar con el codigo: " + codeSaleDetail);
        }

        saleDetailRepository.deleteById(codeSaleDetail);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean existByCodeSaleDetail(Long codeSaleDetail) {
        return saleDetailRepository.existsById(codeSaleDetail);
    }

    private void validateSaleDetail(SaleDetail saleDetail){

        if (saleDetail.getCodeSaleDetail()==null){
            throw new IllegalArgumentException("El codigo de venta no puede estar vacio!");
        }

        if (saleDetail.getAmount()<0 ) {
            throw new IllegalArgumentException("La cantidad de productos es un campo obligatorio y no puede ser menor a 0");
        }

        if (saleDetail.getSubtotal()==null || saleDetail.getSubtotal().compareTo(BigDecimal.ZERO)<0) {
            throw new IllegalArgumentException("El subtotal es un campo obligatorio no puede ser negativo");
        }

        if (saleDetail.getUnit_price()==null ||saleDetail.getUnit_price().compareTo(BigDecimal.ZERO)<0){
            throw new IllegalArgumentException("El precio por unidad no puede ser menor a cero");
        }

        if (saleDetail.getProductCode() == null || saleDetail.getProductCode().getProductCode() == null) {
            throw new IllegalArgumentException("El detalle de la venta debe tener un producto valido");
        }

        if (saleDetail.getSaleCode() == null || saleDetail.getSaleCode().getSaleCode() == null) {
            throw new IllegalArgumentException("El detalle de la venta debe tener una venta valida");
        }

    }
}
