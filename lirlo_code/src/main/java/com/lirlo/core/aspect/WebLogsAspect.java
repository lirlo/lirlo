//package com.lirlo.core.aspect;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//@Slf4j
//public class WebLogsAspect {
//
//    @Pointcut("execution(* com.lirlo..*.*(..))")
//    public void pointCut(){
//
//    }
//
//    @Before("pointCut()")
//    public void doBefore(){
//        log.info("doBefore");
//
//    }
//
//    @After("pointCut()")
//    public void doAfter(){
//        log.info("doAfter");
//    }
//
//    @AfterReturning("pointCut()")
//    public void doAfterTuring(){
//        log.info("doAfterTuring");
//    }
//
//    @Around("pointCut()")
//    public void doAround(ProceedingJoinPoint proceedingJoinPoint){
//
//    }
//}
