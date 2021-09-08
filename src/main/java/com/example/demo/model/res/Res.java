package com.example.demo.model.res;

import com.example.demo.model.entity.Test;
import lombok.Getter;

@Getter
public class Res {
    private final Long id;
    private final String name;

    public Res(final Test test) {
        this.id = test.getId();
        this.name = test.getName();
    }
}
