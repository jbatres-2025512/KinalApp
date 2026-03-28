package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.entity.Sale;
import com.jhulianbatres.kinlapp.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/sales")
public class SaleController {


    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<Sale>> listAll(){

        List<Sale> sale = saleService.listAll();

        return ResponseEntity.ok(sale);
    }





}
