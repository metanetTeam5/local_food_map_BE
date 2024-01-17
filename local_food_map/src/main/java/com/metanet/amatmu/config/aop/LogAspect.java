package com.metanet.amatmu.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class LogAspect {

	@Before("execution(* com.metanet.amatmu.member.controller.MemberController.memberLogin(..))")
	public void beforeLogin(JoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		log.info("로그인 요청");
		log.info("Method name : {} ", method.getName());

		Object[] args = joinPoint.getArgs();

		for (Object obj : args) {
			log.info("Parameter Info : {}", obj);
		}
	}

	@AfterReturning(pointcut = "execution(* com.metanet.amatmu.member.controller.MemberController.memberLogin(..))", returning = "response")
    public void afterReturningLogin(JoinPoint joinPoint, Object response) {
        if (response instanceof ResponseEntity) {
            Object responseBody = ((ResponseEntity<?>) response).getBody();
            log.info("Response: {}", responseBody);
        }
    }
	
	@Before("execution(* com.metanet.amatmu.reservation.controller.ReservationController.insertMemberReservation(..))")
	public void beforeReservation(JoinPoint joinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		log.info("예약 생성");
		log.info("Method name : {} ", method.getName());

		Object[] args = joinPoint.getArgs();

		for (Object obj : args) {
			log.info("Parameter Info : {}", obj);
		}
	}

	@AfterReturning(pointcut = "execution(* com.metanet.amatmu.reservation.controller.ReservationController.insertMemberReservation(..))", returning = "response")
    public void afterReturningReservation(JoinPoint joinPoint, Object response) {
        if (response instanceof ResponseEntity) {
            Object responseBody = ((ResponseEntity<?>) response).getBody();
            log.info("Response: {}", responseBody);
        }
    }
}
