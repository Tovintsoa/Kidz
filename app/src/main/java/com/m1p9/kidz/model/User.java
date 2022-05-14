package com.m1p9.kidz.model;

public class User {

    private String id;
    private String uName;
    private String uUsername;
    private String uEmail;
    private String uPassword;

    public User(){

    }
    public User(String id, String uName, String uUsername, String uEmail, String uPassword) {
        this.id = id;
        this.uName = uName;
        this.uUsername = uUsername;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuUsername() {
        return uUsername;
    }

    public void setuUsername(String uUsername) {
        this.uUsername = uUsername;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }



}
