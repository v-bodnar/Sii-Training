package com.sii.aspects;

import com.sii.exceptions.ConnectionTimeout;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class ExceptionHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @AfterThrowing(pointcut = "execution(* com.sii.transport.MockServer.*(..))", throwing = "connectionTimeout")
    public void handleException(JoinPoint thisJoinPoint, ConnectionTimeout connectionTimeout) {
        LOGGER.error("Connection timeout", connectionTimeout);
        LOGGER.info("Resend message in 60 seconds");
    }

}
