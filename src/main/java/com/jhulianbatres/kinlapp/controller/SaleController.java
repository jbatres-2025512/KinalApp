package com.jhulianbatres.kinlapp.controller;


import com.jhulianbatres.kinlapp.entity.Product;
import com.jhulianbatres.kinlapp.entity.Sale;
import com.jhulianbatres.kinlapp.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{saleCode}")
    public ResponseEntity<Sale>searchBySaleCode(@PathVariable Long saleCode){

        return saleService.findBySaleCode(saleCode)

                .map(ResponseEntity::ok)

                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/actives")
    public ResponseEntity<List<Sale>>findBySaleState(){
        return ResponseEntity.ok(saleService.findBySaleState());
    }

    @PostMapping
    public ResponseEntity<?>save(@RequestBody Sale sale){

        try {

            Sale newSale = saleService.save(sale);
            return new ResponseEntity<>(newSale, HttpStatus.CREATED);

        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
