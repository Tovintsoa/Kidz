package com.m1p9.kidz.service;

public class LoginRequest {
    String uUsername;
    String uPassword;

    public String getUsername() {
        return uUsername;
    }

    public void setUsername(String username) {
        this.uUsername = username;
    }

    public String getPassword() {
        return uPassword;
    }

    public void setPassword(String password) {
        this.uPassword = password;
    }
}
