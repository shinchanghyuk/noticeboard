package com.shin.noticeboard.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class ExecutionTimeCheckAspect {

    // com.shin.noticeboard Controller 에 요청/반환시간 출력
    // 접근제어자(생략O) / 반환타입(생략X) / 선언타입(생략O) / 메서드이름(파라미터)(생략X)
    @Pointcut("execution(* com.shin.noticeboard.controller..*(..))") // 포인트 컷
    public void controllerExecutionTimeCheck() {} // 포인트 컷 시그니쳐

    @Around("controllerExecutionTimeCheck()")
    public Object executionCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        long starttime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.error("ExecutionTimeCheckAspect Exception : ", e);
            throw e;
        } finally {
            long endtime = System.currentTimeMillis();
            log.info("point : {}, resultTime : {}", joinPoint.getTarget(), endtime - starttime);
        }
    }
}
