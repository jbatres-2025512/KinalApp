package com.jhulianbatres.kinlapp.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table (name = "Users")
public class User {

    @Id
    @Column (name="user_code")
    private int userCode;

    @Column
    private String userName;

    @Column
    private String userPassword;

    @Column
    private String userEmail;

    @Column
    private String userRol;

    @Column
    private String userState;

    @OneToMany(mappedBy = "userCode",cascade = CascadeType.ALL)
    private List<Sale> sales;

    public User() {
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRol() {
        return userRol;
    }

    public void setUserRol(String userRol) {
        this.userRol = userRol;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
