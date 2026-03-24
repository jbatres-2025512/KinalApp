package com.jhulianbatres.kinlapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Sales")
public class Sale {

    @Id
    @Column(name = "sale_code")
    private int saleCode;

    @Column (nullable = false)
    private LocalDateTime saleDate;

    @Column
    private double total;

    @Column
    private int saleState;

    @OneToMany(mappedBy = "saleCode",cascade = CascadeType.ALL)
    private List<Sale> saleDetail;

    @ManyToOne
    @JoinColumn(name = "DPIClient",foreignKey = @ForeignKey(name = "FK_client_sale"))
    private Client DPIClient;

    @ManyToOne
    @JoinColumn(name = "userCode",foreignKey = @ForeignKey(name = "FK_user_sale"))
    private User userCode;

    public Sale() {
    }

    public int getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(int saleCode) {
        this.saleCode = saleCode;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getSaleState() {
        return saleState;
    }

    public void setSaleState(int saleState) {
        this.saleState = saleState;
    }

    public List<Sale> getSaleDetail() {
        return saleDetail;
    }

    public void setSaleDetail(List<Sale> saleDetail) {
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
