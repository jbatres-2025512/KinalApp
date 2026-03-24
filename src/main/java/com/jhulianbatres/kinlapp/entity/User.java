package com.jhulianbatres.kinlapp.entity;

import jakarta.persistence.*;


@Entity
@Table (name = "User")
public class User {

    @Id
    @Column (name="user_code")
    private String userCode;

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

    @OneToMany
    @JoinColumn(name = "fk_userCode")
    private User fk_userCode;

    public User() {
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
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

    public User getFk_userCode() {
        return fk_userCode;
    }

    public void setFk_userCode(User fk_userCode) {
        this.fk_userCode = fk_userCode;
    }
}
