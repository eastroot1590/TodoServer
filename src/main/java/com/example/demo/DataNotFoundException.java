package com.example.demo;

import lombok.Getter;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Getter
public class DataNotFoundException extends RuntimeException {
    private int errorCode;

    DataNotFoundException(int code) {
        errorCode = code;
    }
}
