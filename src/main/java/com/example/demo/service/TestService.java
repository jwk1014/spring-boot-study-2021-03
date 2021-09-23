package com.example.demo.service;

import com.example.demo.model.entity.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    // private final TestRepository testRepository;

    public Test getTest(final Long id) {
        // testRepository.getTest(id);
        return Test.builder()
                .id(id)
                .name("woody.jwk")
                .build();
    }
}
