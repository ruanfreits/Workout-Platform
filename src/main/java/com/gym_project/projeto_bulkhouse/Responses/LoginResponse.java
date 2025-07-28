package com.gym_project.projeto_bulkhouse.Responses;

import org.hibernate.jdbc.Expectation;

public class LoginResponse {
    private String token;

    private long expiresIn;

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }
}
