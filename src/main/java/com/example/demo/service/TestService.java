package com.example.demo.service;

import com.example.demo.model.entity.Test;
import com.example.demo.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {
//    private final TestRepository testRepository;
    //private final static Logger log = LoggerFactory.getLogger(TestService.class);
    @Value("${server.port}")
    private Integer port;

    public Test getTest(final Long id) {
        // testRepository.getTest(id);
        log.info("id : "+id);
        return Test.builder()
                .id(id)
                .port(port)
                .build();
    }
}
