package com.sii.aspects;

import com.sii.annotations.Profiled;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

@Aspect
public class Profiler {

    @Pointcut("@annotation(profiled)")
    public void callAt(Profiled profiled){}

    @Around("callAt(profiled)")
    public Object profile(ProceedingJoinPoint pjp,
                          Profiled profiled){
        Logger logger = LoggerFactory.getLogger(pjp.getSignature().getDeclaringType());
        try {
            Instant start = Instant.now();
            Object result = pjp.proceed();
            Duration timeElapsed = Duration.between(start, Instant.now());
            logger.debug("Time taken: " + timeElapsed.toMillis() + " milliseconds");
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
