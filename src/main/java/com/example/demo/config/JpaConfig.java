package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.Optional;

@EnableJpaAuditing(dateTimeProviderRef = "jpaZonedDateTimeProvider")
@Configuration
public class JpaConfig {
    @Bean
    public DateTimeProvider jpaZonedDateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now())
                .map(now -> now.with(ChronoField.MICRO_OF_SECOND, now.get(ChronoField.MILLI_OF_SECOND) * 1000L)); // DATETIME(3)
    }
}
