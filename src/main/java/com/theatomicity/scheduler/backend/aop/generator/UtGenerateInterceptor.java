package com.theatomicity.scheduler.backend.aop.generator;

import com.theatomicity.aop.ut.generator.core.EntryPointAroundPointcut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
@Profile("UtGenerateInterceptor")
public class UtGenerateInterceptor {

    private final EntryPointAroundPointcut entryPointAroundPointcut;

    @Pointcut("execution(* com.theatomicity.scheduler.backend..*(..)) && " +
            " !execution(* com.theatomicity.scheduler.backend.aop..*(..)) && " +
            " !execution(* com.theatomicity.scheduler.backend.config..*(..))")
    public void allMethodsInRootPackage() {
    }

    @Around("allMethodsInRootPackage()")
    public Object logAroundServiceMethods(final ProceedingJoinPoint joinPoint) throws Throwable {
        return this.entryPointAroundPointcut.logAroundServiceMethods(joinPoint);
    }
}
