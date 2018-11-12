package com.sii.service;

import com.sii.interfaces.AuthorizationService;
import com.sii.qualifier.AuthorizationMethod;
import com.sii.qualifier.Stub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

//@Stub
//@AuthorizationMethod
@ApplicationScoped
public class StubAuthorizationService implements AuthorizationService{
    private static final Logger LOGGER = LoggerFactory.getLogger(StubAuthorizationService.class);

    @Override
    public void authorize() {
        LOGGER.info("Authorizing using STUB");
    }
}
