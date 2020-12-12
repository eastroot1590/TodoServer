package com.example.demo;

import lombok.Getter;

@Getter
public class CustomError {
    private int errorCode;
    private String message;

    CustomError(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
