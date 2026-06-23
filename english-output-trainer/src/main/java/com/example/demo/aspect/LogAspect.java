package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {
	
	/** 対象：[Service]をクラス名に含んでいること */
	@Pointcut("execution(* com.example.demo.service.*.*(..))")
	public void serviceMethods(){}
	
	/** サービスの実行前にログ出力する */
	@Before("serviceMethods()")
	public void startLog(JoinPoint jp) {
		log.info("メソッド開始：{}", jp.getSignature());
	}
	/** サービスの実行後にログ出力する */
	@After("serviceMethods()")
	public void endLog(JoinPoint jp) {
		log.info("メソッド終了：{}", jp.getSignature());
	}

}