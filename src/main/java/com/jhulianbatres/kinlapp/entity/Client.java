package com.jhulianbatres.kinlapp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "clients")
public class Client {

    @Id
    @Column (name = "dpi_client")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String DPIClient;

    @Column
    private String nameClient;

    @Column
    private String lastNameClient;

    @Column
    private String address;

    @Column
    private Integer state;

    @OneToMany(mappedBy = "DPIClient", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Sale> sale;

    public Client() {
    }

    public String getDPIClient() {
        return DPIClient;
    }

    public void setDPIClient(String DPIClient) {
        this.DPIClient = DPIClient;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getLastNameClient() {
        return lastNameClient;
    }

    public void setLastNameClient(String lastNameClient) {
        this.lastNameClient = lastNameClient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<Sale> getSale() {
        return sale;
    }

    public void setSale(List<Sale> sale) {
        this.sale = sale;
    }
}
