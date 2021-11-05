package com.example.demo.model.mongoEntity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String name;
}
