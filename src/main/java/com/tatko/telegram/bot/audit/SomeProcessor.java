package com.tatko.telegram.bot.audit;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SomeProcessor {

    @Pointcut("@annotation(com.tatko.telegram.bot.audit.OnUpdateReceivedBeforeProcessorAnnotation)")
    public void auditPoint() {
    }

    @Around("auditPoint()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

//        log.debug("Audit @Around advice: {}", proceedingJoinPoint);
//        Signature signature = proceedingJoinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method method = methodSignature.getMethod();

        return proceedingJoinPoint.proceed();

    }


}
