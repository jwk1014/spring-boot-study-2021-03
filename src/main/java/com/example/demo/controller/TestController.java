package com.example.demo.controller;

import com.example.demo.model.entity.Test;
import com.example.demo.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/tests/{id}")
    public Test getTest(
            @PathVariable("id") Long id
    ) {
        return testService.getTest(id);
    }
}