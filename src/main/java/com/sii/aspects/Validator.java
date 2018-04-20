package com.sii.aspects;

import com.sii.exceptions.ValidationException;
import com.sii.model.AbstractRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class Validator {
    private static Logger LOGGER = LoggerFactory.getLogger(Validator.class);

    @Around("execution(public * * (@com.sii.annotations.Validate (*))) && args(abstractRequest)")
    public Object profile(ProceedingJoinPoint pjp, AbstractRequest abstractRequest) {
        try {
            com.sii.annotations.processor.Validator.validate(abstractRequest);
            return pjp.proceed();
        } catch (ValidationException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
