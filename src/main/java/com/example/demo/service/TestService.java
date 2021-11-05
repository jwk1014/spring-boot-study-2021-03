package com.example.demo.service;

import com.example.demo.model.entity.Test;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.concurrent.Executor;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {
    // private final TestRepository testRepository;
    private final MessageSource messageSource;

    public Test getTest(final Long id) {
        // testRepository.getTest(id);
        return Test.builder()
                .id(id)
                .name("woody.jwk")
                .build();
    }

    public String getName(final Locale locale) {
        return messageSource.getMessage("test.name", null, Locale.US);
    }

    @Async
    public void testAsync() {
        for(int i=1;i<=5;i++) {
            log.info("test "+i);
            try {
              Thread.sleep(10 * 1000L);
            } catch (Exception e) { }
        }
    }

    //@Scheduled(cron = "*/5 * * * * *")
    public void testSchedule() {
        log.info("testSchedule");
    }
}
