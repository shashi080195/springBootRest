package com.example.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class RedundantUserException extends RuntimeException {
    public RedundantUserException(String exception) {
        super(exception);
    }

}
