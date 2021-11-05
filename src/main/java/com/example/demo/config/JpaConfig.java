package com.example.demo.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
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

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em){
        return new JPAQueryFactory(em);
    }
}
