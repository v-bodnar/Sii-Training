package com.sii.service;

import com.sii.PaymentTerminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;


//@ApplicationScoped
//public class ElamPaymentTerminal implements PaymentTerminal {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ElamPaymentTerminal.class);
//    private static final int MULTIPLIER = 10;
//
//    @PostConstruct
//    private void init() {
//        LOGGER.info("Initializing Elam");
//    }
//
//    @Override
//    public void makeTransaction(int amount) {
//        LOGGER.info("Payment transaction for amount: {}", amount * MULTIPLIER);
//    }
//}
