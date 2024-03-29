package com.example.demo.controller.aspect;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AnnotatedPointCutAspect {

    @Pointcut("@annotation(com.example.demo.controller.customannotations.ExecutionLog)")
    public void methodAnnotatedWithExecutionLog() {
    }

    @Around("methodAnnotatedWithExecutionLog()")
    public Object aroundAdviceExecutionLog(ProceedingJoinPoint pjp) throws Throwable {
        try {
            log.info(
                    format("Method %s executed with %s arguments , started at - %s",
                            pjp.getSignature(),
                            Arrays.toString(pjp.getArgs()), new Date().toString()));
            Object resp = pjp.proceed(pjp.getArgs());
            log.info(
                    format("Method %s executed with %s arguments , completed at - %s",
                            pjp.getSignature(),
                            Arrays.toString(pjp.getArgs()), new Date().toString()));
            return resp;
        } catch (Throwable e) {
            log.error(
                    format("Method %s executed with %s arguments , completed at - %s,with error - %s",
                            pjp.getSignature(),
                            Arrays.toString(pjp.getArgs()), new Date().toString(), e.getMessage()));
            throw e;
        }
    }
}