package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.entity.SaleDetail;
import com.jhulianbatres.kinlapp.service.SaleDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saleDetails")
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

    @GetMapping("/{codeSaleDetail}")
    public ResponseEntity<SaleDetail> searchBySaleDetailCode(@PathVariable Long codeSaleDetail){

        return saleDetailService.findBySaleDetailCode(codeSaleDetail)
                .map(ResponseEntity::ok)

                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody SaleDetail saleDetail){

        try {
            SaleDetail newSaleDetail = saleDetailService.save(saleDetail);

            return new ResponseEntity<>(newSaleDetail, HttpStatus.CREATED);

        }catch (IllegalArgumentException e){

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("/{codeSaleDetail}")
    public ResponseEntity<?>update(@PathVariable Long codeSaleDetail,@RequestBody SaleDetail saleDetail){
      try{

        if (!saleDetailService.existByCodeSaleDetail(codeSaleDetail)){
            return ResponseEntity.notFound().build();
        }

        SaleDetail updatedSailDetail = saleDetailService.update(codeSaleDetail,saleDetail);

        return ResponseEntity.ok(updatedSailDetail);

      }catch (IllegalArgumentException e){

          return ResponseEntity.badRequest().body(e.getMessage());

      }catch (RuntimeException e){

          return ResponseEntity.notFound().build();

      }

    }

    @DeleteMapping("/{codeSaleDetail}")
    public ResponseEntity<Void> delete(@PathVariable Long codeSaleDetail){

        try {

            if (!saleDetailService.existByCodeSaleDetail(codeSaleDetail)){
                return ResponseEntity.noContent().build();
            }

            saleDetailService.delete(codeSaleDetail);

            return ResponseEntity.noContent().build();

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }

    }

}
