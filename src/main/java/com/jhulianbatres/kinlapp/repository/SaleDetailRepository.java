package com.jhulianbatres.kinlapp.repository;

import com.jhulianbatres.kinlapp.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository <SaleDetail,Long> {

    List<SaleDetail> findBySaleCode_SaleCode(Long saleCode);

}
