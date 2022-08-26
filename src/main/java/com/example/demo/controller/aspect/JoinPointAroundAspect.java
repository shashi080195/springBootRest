// package com.example.demo.controller.aspect;

// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Pointcut;
// import org.springframework.stereotype.Component;

// import java.util.Arrays;
// import java.util.Date;
// import java.util.logging.Logger;

// import static java.lang.String.format;

// @Aspect
// @Component
// public class JoinPointAroundAspect {

// private static final java.util.logging.Logger log = Logger
// .getLogger(JoinPointAroundAspect.class.getName());

// @Pointcut("execution(*
// com.example.demo.service.UserDetailsServiceImpl.getAllUser())")
// public void getAllUserPointCut() {
// }

// @Around("getAllUserPointCut()")
// public Object aroundAdviceException(ProceedingJoinPoint pjp) throws Throwable
// {
// try {
// log.info(
// format("Method %s executed with %s arguments , started at - %s",
// pjp.getSignature(),
// Arrays.toString(pjp.getArgs()), new Date().toString()));
// Object resp = pjp.proceed(pjp.getArgs());
// log.info(
// format("Method %s executed with %s arguments , completed at - %s",
// pjp.getSignature(),
// Arrays.toString(pjp.getArgs()), new Date().toString()));
// return resp;
// } catch (Throwable e) {
// log.warning(
// format("Method %s executed with %s arguments , completed at - %s,with error -
// %s",
// pjp.getSignature(),
// Arrays.toString(pjp.getArgs()), new Date().toString(), e.getMessage()));
// throw e;
// }
// }
// }
