package com.sii.service;

import com.sii.PaymentTerminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@ApplicationScoped
public class StubPaymentTerminal implements PaymentTerminal {
    private static final Logger LOGGER = LoggerFactory.getLogger(StubPaymentTerminal.class);

    @PostConstruct
    private void init() {
        LOGGER.info("Initializing...");
    }

    @PreDestroy
    private void destroy() {
        LOGGER.info("Destroying...");
    }

    @Override
    public void makeTransaction(int amount) {
        LOGGER.info("Payment transaction for amount: {}", amount);
    }
}