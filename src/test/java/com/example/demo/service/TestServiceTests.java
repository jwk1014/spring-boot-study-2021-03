package com.example.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestServiceTests {
    @Autowired
    private TestService testService;

    @Test
    public void getTest() {
        Assertions.assertEquals(
                testService.getTest(1L).getId(),
                1L
        );
    }

    @Test
    public void getTest2() {
        Assertions.assertEquals(
                testService.getTest(2L).getId(),
                2L
        );
    }
}
