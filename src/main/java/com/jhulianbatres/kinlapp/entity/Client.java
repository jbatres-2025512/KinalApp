package com.jhulianbatres.kinlapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "clients")
public class Client {

    @Id
    @Column (name = "dpi_client")
    private String DPIClient;
    @Column
    private String nameClient;
    @Column
    private String lastNameClient;
    @Column
    private String address;
    @Column
    private Integer state;

    public Client() {
    }

    public Client(String DPIClient, String nameClient, String lastNameClient, String addressClient, Integer clientState) {
        this.DPIClient = DPIClient;
        this.nameClient = nameClient;
        this.lastNameClient = lastNameClient;
        this.address = addressClient;
        this.state = clientState;
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



}
