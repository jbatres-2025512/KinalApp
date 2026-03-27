package com.jhulianbatres.kinlapp.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @Column(name = "product_code")
    private Long productCode;

    @Column
    private String productName;

    @Column
    private BigDecimal price;

    @Column
    private int stock;

    @Column
    private int productState;

    @OneToMany(mappedBy = "productCode",cascade = CascadeType.ALL)
    private List<SaleDetail> saleDetail;


    public Product() {
    }

    public Long getProductCode() {
        return productCode;
    }

    public void setProductCode(Long productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getProductState() {
        return productState;
    }

    public void setProductState(int productState) {
        this.productState = productState;
    }

    public List<SaleDetail> getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(List<SaleDetail> saleDetail) {
        this.saleDetail = saleDetail;
    }
}
