package com.theatomicity.scheduler.backend.aop.generator;

import com.theatomicity.scheduler.backend.aop.generator.pojo.InterceptedParam;
import com.theatomicity.scheduler.backend.aop.generator.pojo.MethodExecution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
@Profile("UtGenerateInterceptor")
public class UtGenerateInterceptor {

    private final TestClassGenerator testClassGenerator;

    private final MethodExecutionCache cache;

    @Pointcut("execution(* com.theatomicity.scheduler.backend..*(..)) && " +
            " !execution(* com.theatomicity.scheduler.backend.aop..*(..)) && " +
            " !execution(* com.theatomicity.scheduler.backend.config..*(..))")
    public void allMethodsInRootPackage() {
    }

    @Around("allMethodsInRootPackage()")
    public Object logAroundServiceMethods(final ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodExecution methodExecution = MethodExecution.from(joinPoint);
        log.info("Before MethodExecution: {} {} {}", methodExecution.getClassName(), methodExecution.getName(), methodExecution.getInputParams());
        final Object result;
        try {
            result = joinPoint.proceed();
            if (Objects.nonNull(result)) {
                methodExecution.setResult(new InterceptedParam("result", result.getClass(), result));
            }
        } catch (final Throwable e) {
            log.error("Error during execution of method: {} {}", methodExecution.getClassName(), methodExecution.getName(), e);
            throw e;
        } finally {
            log.info("End MethodExecution: {} {} {}", methodExecution.getClassName(), methodExecution.getName(), methodExecution.getResult());
            methodExecution.setEndTime(System.nanoTime());
            this.cache.add(methodExecution);
            this.testClassGenerator.generateUnitTest(methodExecution);
        }
        return result;
    }
}
