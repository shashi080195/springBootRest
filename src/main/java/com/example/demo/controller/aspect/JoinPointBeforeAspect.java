// package com.example.demo.controller.aspect;

// import org.aspectj.lang.JoinPoint;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Before;
// import org.aspectj.lang.annotation.Pointcut;
// import org.springframework.stereotype.Component;

// import java.util.Arrays;
// import java.util.logging.Logger;

// import static java.lang.String.format;

// @Aspect
// @Component
// public class JoinPointBeforeAspect {

// private static final Logger log =
// Logger.getLogger(JoinPointBeforeAspect.class.getName());

// @Pointcut("execution(*
// com.example.demo.service.UserDetailsServiceImpl.getAllUser())")
// public void getAllUserPointCut() {
// }

// @Before("getAllUserPointCut()")
// public void beforeAdvice(JoinPoint joinPoint) {
// log.info(
// format("Method %s executed with %s arguments",
// joinPoint.getSignature(),
// Arrays.toString(joinPoint.getArgs())));
// }
// }
