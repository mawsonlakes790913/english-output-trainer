package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {
	
	/** 対象：[Service]をクラス名に含んでいること */
	@Pointcut("execution(* com.example.demo.service.*.*(..))")
	public void serviceMethods(){}
	
	@Around("serviceMethods()")
	public Object measure(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = jp.proceed();

        long end = System.currentTimeMillis();

        log.info(
            "{} : {}ms",
            jp.getSignature().getName(),
            end - start
        );

        return result;
	}
}