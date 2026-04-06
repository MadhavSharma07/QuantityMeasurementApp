package com.app.quantitymeasurementapp.model;

public class AuthResponse {

    private String token;
    private String username;
    private String role;

    public AuthResponse() {}

    public AuthResponse(String token, String username, String role) {
        this.token    = token;
        this.username = username;
        this.role     = role;
    }

    public String getToken()    { return token; }
    public String getUsername() { return username; }
    public String getRole()     { return role; }

    public void setToken(String t)    { this.token = t; }
    public void setUsername(String u) { this.username = u; }
    public void setRole(String r)     { this.role = r; }
}