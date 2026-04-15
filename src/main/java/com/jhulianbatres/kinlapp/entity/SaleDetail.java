package com.jhulianbatres.kinlapp.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_detail")
public class SaleDetail {

    @Id
    @Column(name = "code_saleDetail")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeSaleDetail;

    @Column
    private int amount;

    @Column
    private BigDecimal unit_price;

    @Column
    private BigDecimal subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCode",foreignKey = @ForeignKey(name = "FK_product_saleDetail"))
    private Product productCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saleCode",foreignKey = @ForeignKey(name = "FK_sale_saleDetail"))
    private Sale saleCode;

    public SaleDetail() {
    }

    public Long getCodeSaleDetail() {
        return codeSaleDetail;
    }

    public void setCodeSaleDetail(Long codeSaleDetail) {
        this.codeSaleDetail = codeSaleDetail;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
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
