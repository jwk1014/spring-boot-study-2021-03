package com.example.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TestAspect {

    @Around("execution(* com.example.demo.service.TestService.getTest(..))")
    public Object beforeGetTest(ProceedingJoinPoint joinPoint) throws Throwable {
        Object response = null;
        try {
            response = joinPoint.proceed();
        } catch (Throwable t) {
            throw t;
        }
        return response;
    }

    @Pointcut("execution(* com.example.demo.repository.PostRepository.findById(..))")
    public void pountCutFindById() {}

    @Before("pountCutFindById() && args(id)")
    public void beforeFindById(final Long id) {
        log.info("beforeFindById");
    }

    @After("pountCutFindById() && args(id)")
    public void afterFindById(final Long id) {
        log.info("afterFindById");
    }

    @Before("@annotation(TestPoint)")
    public void beforeGetPost() {
        log.info("beforeGetPost");
    }
}
