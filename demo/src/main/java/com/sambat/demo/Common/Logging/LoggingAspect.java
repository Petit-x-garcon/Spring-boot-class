package com.sambat.demo.Common.Logging;

import com.sambat.demo.Common.Constant.RequestConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
//@Component
@Slf4j
@Order(2)
public class LoggingAspect {
    @Autowired
    private LogFormatter logFormatter;

    @Around("execution(* com.sambat.demo.Service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable{
        String target = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();

        String requestId = MDC.get(RequestConstant.REQUEST_ID);
        String httpMethod = MDC.get(RequestConstant.HTTP_METHOD);
        String requestPath = MDC.get(RequestConstant.REQUEST_PATH);

        log.info(logFormatter.requestLog(requestId, target, method, httpMethod, requestPath, startTime));

        try{
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.info(logFormatter.responseLog(requestId, target, method, httpMethod, requestPath, startTime, endTime));
            return result;
        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            log.error(logFormatter.errorLog(requestId, target, method, httpMethod, requestPath, startTime, endTime));
            throw e;
        }
    }

    @Around("execution(* com.sambat.demo.Controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable{
        String target = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();

        String requestId = MDC.get(RequestConstant.REQUEST_ID);
        String httpMethod = MDC.get(RequestConstant.HTTP_METHOD);
        String requestPath = MDC.get(RequestConstant.REQUEST_PATH);

        log.info(logFormatter.requestLog(requestId, target, method, httpMethod, requestPath, startTime));

        try{
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.info(logFormatter.responseLog(requestId, target, method, httpMethod, requestPath, startTime, endTime));
            return result;
        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            log.error(logFormatter.errorLog(requestId, target, method, httpMethod, requestPath, startTime, endTime));
            throw e;
        }
    }

    @Around("execution(* com.sambat.demo.Repository..*(..))")
    public Object logRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable{
        String target = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();

        String requestId = MDC.get(RequestConstant.REQUEST_ID);
        String httpMethod = MDC.get(RequestConstant.HTTP_METHOD);
        String requestPath = MDC.get(RequestConstant.REQUEST_PATH);

        log.info(logFormatter.requestLog(requestId, target, method, httpMethod, requestPath, startTime));

        try{
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.info(logFormatter.responseLog(requestId, target, method, httpMethod, requestPath, startTime, endTime));
            return result;
        }catch (Exception e){
            long endTime = System.currentTimeMillis();
            log.error(logFormatter.errorLog(requestId, target, method, httpMethod, requestPath, startTime, endTime));
            throw e;
        }
    }
}
