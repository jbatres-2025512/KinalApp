package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.Sale;
import com.jhulianbatres.kinlapp.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

@Transactional

public class SaleService implements ISaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sale> listAll() {
        return saleRepository.findAll();
    }

    @Override
    public Sale save(Sale sale) {
        validateSale(sale);
        return saleRepository.save(sale);
    }

    @Override
    public Optional<Sale> findBySaleCode(Long saleCode) {

        if (!saleRepository.existsById(saleCode)){
            throw new RuntimeException("No se encontro ninguna venta con el codigo: " + saleCode);
        }

        return saleRepository.findById(saleCode);
    }

    @Override
    public List<Sale> findBySaleState() {
        return saleRepository.findAll().stream().filter(saleState -> saleState.getSaleState() !=0).collect(Collectors.toList());
    }

    @Override
    public Sale update(Long saleCode, Sale sale) {
        if (!saleRepository.existsById(saleCode)){
            throw new RuntimeException("No se encontro ninguna venta con el codigo: " + saleCode);
        }

        sale.setSaleCode(saleCode);

        validateSale(sale);

        return saleRepository.save(sale);

    }

    @Override
    public void delete(Long saleCode) {
        if (!saleRepository.existsById(saleCode)){
            throw new RuntimeException("No se encontro ninguna venta con el codigo: " + saleCode);
        }

        saleRepository.deleteById(saleCode);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean exstBySaleCode(Long saleCode) {
        return saleRepository.existsById(saleCode);
    }

    private void validateSale(Sale sale){

        if (sale.getSaleCode()==null){
            throw new IllegalArgumentException("El codigo de venta es un campo obligatorio");
        }

        if (sale.getTotal()==null || sale.getTotal().compareTo(BigDecimal.ZERO)<0 ){
            throw new IllegalArgumentException("El total es un campo obligatorio y no puede ser un numero negativo");
        }

        if (sale.getDPIClient() == null || sale.getDPIClient().getDPIClient() == null) {
            throw new IllegalArgumentException("La venta debe tener a un cliente válido");
        }

        if (sale.getUserCode() == null || sale.getUserCode().getUserCode() == null) {
            throw new IllegalArgumentException("La venta debe tener un usuario asignado");
        }

        if (sale.getSaleState() < 0) {
            throw new IllegalArgumentException("El estado de la venta no puede ser negativo");
        }



    }



}
