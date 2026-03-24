package com.jhulianbatres.kinlapp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @Column(name = "product_code")
    private int productCode;

    @Column
    private String productName;

    @Column
    private float price;

    @Column
    private int stock;

    @Column
    private int productState;

    @OneToMany(mappedBy = "productCode",cascade = CascadeType.ALL)
    private List<Product> saleDetail;


    public Product() {
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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

    public List<Product> getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(List<Product> saleDetail) {
        this.saleDetail = saleDetail;
    }
}
