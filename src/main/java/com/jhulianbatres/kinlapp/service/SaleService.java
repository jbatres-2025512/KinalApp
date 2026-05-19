package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.Sale;
import com.jhulianbatres.kinlapp.repository.ClientRepository;
import com.jhulianbatres.kinlapp.repository.SaleRepository;
import com.jhulianbatres.kinlapp.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public SaleService(SaleRepository saleRepository, ClientRepository clientRepository, UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
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
        return saleRepository.findById(saleCode);
    }

    @Override
    public List<Sale> findBySaleState() {
        return saleRepository.findAll().stream().filter(saleState -> saleState.getSaleState() !=0).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Sale update(Long saleCode, Sale sale) {

        Sale saleExistente = saleRepository.findById(saleCode)
                .orElseThrow(() -> new RuntimeException("No se encontró ninguna venta con el código: " + saleCode));

        saleExistente.setSaleState(sale.getSaleState());

        validateSale(saleExistente);

        return saleRepository.save(saleExistente);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void delete(Long saleCode) {
        if (!saleRepository.existsById(saleCode)){
            throw new RuntimeException("No se encontro ninguna venta con el codigo: " + saleCode);
        }

        saleRepository.deleteById(saleCode);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean existBySaleCode(Long saleCode) {
        return saleRepository.existsById(saleCode);
    }

    private void validateSale(Sale sale){

        if (sale.getTotal() == null || sale.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El total es obligatorio y no puede ser negativo");
        }

        if (sale.getDPIClient() == null || sale.getDPIClient().getDPIClient() == null) {
            throw new IllegalArgumentException("La venta debe tener a un cliente válido");
        }


        if (!clientRepository.existsById(sale.getDPIClient().getDPIClient())) {
            throw new IllegalArgumentException("El DPI del cliente no existe en el sistema.");
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