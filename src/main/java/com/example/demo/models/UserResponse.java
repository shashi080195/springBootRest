package com.example.demo.models;

public class UserResponse {
    private String success;
    private String message;

    public UserResponse() {

    }

    public UserResponse(String success, String message) {
        this.success = success;
        this.message = message;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }
}