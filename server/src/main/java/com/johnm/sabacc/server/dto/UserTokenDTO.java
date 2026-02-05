package com.johnm.sabacc.server.dto;

public class UserTokenDTO {
    private String token;

    public UserTokenDTO(String token) { this.token = token; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }
}
