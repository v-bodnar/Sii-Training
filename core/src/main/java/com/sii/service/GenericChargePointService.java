package com.sii.service;

import com.sii.ChargePointService;
import com.sii.PaymentTerminal;
import com.sii.model.ChargePoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;

@ApplicationScoped
public class GenericChargePointService implements ChargePointService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericChargePointService.class);

    @Inject
    private PaymentTerminal paymentTerminal;

    @Inject
    private ChargePoint chargePoint;

//    public GenericChargePointService(PaymentTerminal paymentTerminal) {
//        this.paymentTerminal = paymentTerminal;
//    }

    @PostConstruct
    private void init() {
        LOGGER.info("Charge Point Service Initialized");
    }

    @PreDestroy
    private void destroy() {
        LOGGER.info("Destroying...");
        stopCharging();
    }

    @Override
    public void startCharging() {
        LOGGER.info("Starting charging");
        chargePoint.setCharging(true);
        chargePoint.setChargingStartedAt(Instant.now());
    }

    @Override
    public void stopCharging() {
        LOGGER.info("Stop charging");
        paymentTerminal.makeTransaction((int) Duration.between(chargePoint.getChargingStartedAt(), Instant.now()).getSeconds());
        chargePoint.setCharging(false);
    }

    @Override
    public boolean isCharging() {
        return chargePoint.isCharging();
    }

//    @Inject
//    public void setPaymentTerminal(PaymentTerminal paymentTerminal) {
//        this.paymentTerminal = paymentTerminal;
//    }
}
