package com.sii.service;

import com.sii.interfaces.AuthorizationService;
import com.sii.qualifier.Configured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class AuthorizationServiceFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(StubAuthorizationService.class);
    private static final String authorizationType = "";

    @Produces
    @Configured
    public AuthorizationService getPaymentTerminal(StubAuthorizationService stubAuthorizationService,
                                                   RfidAuthorizationService rfidAuthorizationService) {
        switch (authorizationType) {
            case ("RFID"):
                LOGGER.info("Injecting RFID Authorization Service");
                return rfidAuthorizationService;
            default:
                LOGGER.info("Injecting STUB Authorization Service");
                return stubAuthorizationService;
        }
    }
}
