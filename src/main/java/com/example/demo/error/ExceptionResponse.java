package com.example.demo.error;

import java.util.Date;

import com.example.demo.models.UserResponse;

public class ExceptionResponse extends UserResponse {
  private String details;

  public ExceptionResponse(String message, String details) {
    super("0", message);
    this.details = details;
  }

  public String getDetails() {
    return details;
  }

}