package com.jhulianbatres.kinlapp.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Sales")
public class Sale {

    @Id
    @Column(name = "sale_code")
    private Long saleCode;

    @Column (nullable = false,updatable = false)

    private LocalDateTime saleDate;

    @PrePersist
    protected void onCreate(){
        this.saleDate = LocalDateTime.now();
    }

    @Column
    private BigDecimal total;

    @Column
    private int saleState;

    @OneToMany(mappedBy = "codeSaleDetail",cascade = CascadeType.ALL)
    private List<SaleDetail> saleDetail;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "DPIClient",foreignKey = @ForeignKey(name = "FK_client_sale"))
    private Client DPIClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code",foreignKey = @ForeignKey(name = "FK_user_sale"))
    private User userCode;

    public Sale() {
    }

    public Long getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(Long saleCode) {
        this.saleCode = saleCode;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getSaleState() {
        return saleState;
    }

    public void setSaleState(int saleState) {
        this.saleState = saleState;
    }

    public List<SaleDetail> getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(List<SaleDetail> saleDetail) {
        this.saleDetail = saleDetail;
    }

    public Client getDPIClient() {
        return DPIClient;
    }

    public void setDPIClient(Client DPIClient) {
        this.DPIClient = DPIClient;
    }

    public User getUserCode() {
        return userCode;
    }

    public void setUserCode(User userCode) {
        this.userCode = userCode;
    }
}
