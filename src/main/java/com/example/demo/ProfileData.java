package com.example.demo;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("profile")
@Getter
@Setter
public class ProfileData {
    @Id
    private String id;

    private String uid;
    private String name;
    private String profileImagePath;
    private int tier;
    private int exp;
}

