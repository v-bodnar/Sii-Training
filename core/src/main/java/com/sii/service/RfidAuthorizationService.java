package com.sii.service;


import com.sii.interfaces.AuthorizationService;
import com.sii.qualifier.AuthorizationMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

//@AuthorizationMethod("RFID")
@ApplicationScoped
public class RfidAuthorizationService implements AuthorizationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RfidAuthorizationService.class);

    @Override
    public void authorize() {
        LOGGER.info("Authorizing using RFID");
    }
}
