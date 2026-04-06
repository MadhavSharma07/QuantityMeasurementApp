package com.app.quantitymeasurementapp.model;

public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setUsername(String u) { this.username = u; }
    public void setPassword(String p) { this.password = p; }
}