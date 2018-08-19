package com.example.demo.models;

public class OtpModel extends UserResponse {
    private boolean isRegistered;
    private String email;

    public OtpModel() {

    }

    public OtpModel(String success, String message, boolean isRegistered, String email) {
        super(success, message);
        this.isRegistered = isRegistered;
        this.email = email;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public boolean getIsRegistered() {
        return this.isRegistered;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}