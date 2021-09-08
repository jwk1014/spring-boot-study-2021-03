package com.example.demo.controller;

import com.example.demo.component.ServerManager;
import com.example.demo.model.res.Res;
import com.example.demo.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final ServerManager serverManager;
    private final TestService testService;

    @GetMapping("/test")
    public Map getTest(
    ) {
        return Map.of("serverPort", serverManager.getPort());
    }

    @GetMapping("/tests/{id}")
    public Res getTestById(
            @PathVariable Long id
    ) {
       return new Res(
               testService.getTest(id)
       );
    }
}