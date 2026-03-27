package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.SaleDetail;
import com.jhulianbatres.kinlapp.repository.SaleDetailRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

@Transactional

public class SaleDetailService implements ISaleDetail {

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
    public SaleDetail save(SaleDetail saleDetailService) {
        return saleDetailRepository.save(saleDetailService);
    }

    @Override
    public Optional<SaleDetail> findBySaleDetailCode(Long codeSaleDetail) {

        if (!saleDetailRepository.existsById(codeSaleDetail))
            throw new RuntimeException("No se encontro ningun detalle de venta con el codigo: " + codeSaleDetail);

        return saleDetailRepository.findById(codeSaleDetail);
    }

    @Override
    public SaleDetail update(Long codeSaleDetail, SaleDetail saleDetailService) {
        if (!saleDetailRepository.existsById(codeSaleDetail))
            throw new RuntimeException("No se encontro nigun detalle de venta para actualizar con el codigo: " + codeSaleDetail);
        saleDetailService.setCodeSaleDetail(codeSaleDetail);

        return saleDetailRepository.save(saleDetailService);

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
}
