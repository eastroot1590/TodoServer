package com.example.demo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpData {
    private String token;
    private String name;
    private byte[] profileImage;
}
