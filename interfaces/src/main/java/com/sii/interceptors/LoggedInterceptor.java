package com.sii.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

//@Logged @Interceptor @Priority(1)
//public class LoggedInterceptor {
//    @AroundInvoke
//    public Object log(InvocationContext ctx) throws Exception {
//        Logger logger = LoggerFactory.getLogger(ctx.getTarget().getClass().getSuperclass());
//        logger.info("Method invoked {}, params: {}", ctx.getMethod().getName(), ctx.getMethod().getParameters());
//        return ctx.proceed();
//    }
//}
