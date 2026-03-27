package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.entity.SaleDetail;
import com.jhulianbatres.kinlapp.service.SaleDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/saleDetails")
public class SaleDetailController {

    private final SaleDetailService  saleDetailService;

    public SaleDetailController(SaleDetailService saleDetailService) {
        this.saleDetailService = saleDetailService;
    }

    @GetMapping
    public ResponseEntity<List<SaleDetail>> list(){
        List<SaleDetail> saleDetails = saleDetailService.listAll();

        return ResponseEntity.ok(saleDetails);
    }

    
}
