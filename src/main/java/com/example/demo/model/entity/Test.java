package com.example.demo.model.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Test {
    private final Long id;
    private final String name;
}
