package com.example.demo.aspect;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Aspect
@Component
@Slf4j
public class ErrorLogAspect {
	@Pointcut("bean(*Repository)")
	public void repositoryLayer() {}
	
	@Pointcut("bean(*Service*)")
	public void serviceLayer() {}
	
	@Pointcut("bean(*Controller)")
	public void controllerLayer() {}
	
	@Pointcut("repositoryLayer() || serviceLayer() || controllerLayer()")
	public void applicationLayer() {}
	
	@AfterThrowing(
		    value = "applicationLayer()",
		    throwing = "ex"
		)
		public void logError(Exception ex) {

		    log.error(
		        "例外発生 [{}] {}",
		        ex.getClass().getSimpleName(),
		        ex.getMessage(),
		        ex
		    );
		}
	
	
}