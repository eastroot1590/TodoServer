package com.example.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("profile")
@Getter
@Setter
public class Info {
    private String title;
    private String content;
}
