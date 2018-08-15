package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadEntryRequestException extends RuntimeException {
	public BadEntryRequestException(String exception){
		super(exception);
	}
}