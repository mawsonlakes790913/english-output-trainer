package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
		// 認証情報取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 認証情報から名前を取得(未ログイン対策あり)
        String userId = "anonymous";
        if (authentication != null) {
            userId = authentication.getName();
        }
        // ログ出力
		log.info("ユーザーID={}, メソッド開始(Service): {}", userId, jp.getSignature());
	}
	/** サービスの実行後にログ出力する */
	@After("serviceMethods()")
	public void endLog(JoinPoint jp) {
		// 認証情報取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 認証情報から名前を取得(未ログイン対策あり)
        String userId = "anonymous";

        if (authentication != null) {
            userId = authentication.getName();
        }
        // ログ出力
        log.info("ユーザーID={}, メソッド終了(Service): {}", userId, jp.getSignature());
	}

}