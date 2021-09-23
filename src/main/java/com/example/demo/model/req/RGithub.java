package com.example.demo.model.req;

import lombok.Getter;

public class RGithub {
    @Getter
    public static class ListGetRepo {
        private Long id;
        private String name;
    }
}
