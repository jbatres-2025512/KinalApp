package com.jhulianbatres.kinlapp.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "sale_detail")
public class SaleDetail {

    @Id
    @Column(name = "code_saleDetail")
    private int codeSaleDetail;

    @Column
    private int amount;

    @Column
    private double unit_price;

    @Column
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "productCode",foreignKey = @ForeignKey(name = "FK_product_saleDetail"))
    private Product productCode;

    @ManyToOne
    @JoinColumn(name = "saleCode",foreignKey = @ForeignKey(name = "FK_sale_saleDetail"))
    private Sale saleCode;

    public SaleDetail() {
    }

    public int getCodeSaleDetail() {
        return codeSaleDetail;
    }

    public void setCodeSaleDetail(int codeSaleDetail) {
        this.codeSaleDetail = codeSaleDetail;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Product getProductCode() {
        return productCode;
    }

    public void setProductCode(Product productCode) {
        this.productCode = productCode;
    }

    public Sale getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(Sale saleCode) {
        this.saleCode = saleCode;
    }
}
