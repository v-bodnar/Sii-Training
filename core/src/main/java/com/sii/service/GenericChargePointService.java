package com.sii.service;

import com.sii.action.LogReader;
import com.sii.events.ChargingStartedEvent;
import com.sii.interceptors.Logged;
import com.sii.interfaces.AuthorizationService;
import com.sii.interfaces.ChargePointService;
import com.sii.interfaces.PaymentTerminal;
import com.sii.model.ChargePoint;
import com.sii.qualifier.Configured;
import com.sii.qualifier.Stub;
import javafx.util.Callback;
import org.jboss.weld.environment.se.contexts.activators.ActivateThreadScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

@ApplicationScoped
//@ActivateThreadScope
public class GenericChargePointService implements ChargePointService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericChargePointService.class);

//    @Inject
//    private LogReader logReader;

//    @Inject
//    private Logger LOGGER;

//    @Inject @Any
//    private Event<ChargingStartedEvent> chargePointEvent;

    @Inject
    private PaymentTerminal paymentTerminal;

//    @Inject @Configured
//    private AuthorizationService authorizationService;

//    @Inject
    private ChargePoint chargePoint = new ChargePoint();

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
    @Logged
    public void startCharging() {
        LOGGER.info("Starting charging");
//        authorizationService.authorize();
        chargePoint.setCharging(true);
        chargePoint.setChargingStartedAt(Instant.now());
//        chargePointEvent.fire(new ChargingStartedEvent());
    }

    @Override
    public void getLogs() {
//        logReader.execute(param -> {
//            LOGGER.info(param.toString());
//            return param;
//        });
    }

    @Override
    public void stopCharging() {
        LOGGER.info("Stop charging");
        if(chargePoint.isCharging()) {
            paymentTerminal.makeTransaction((int) Duration.between(chargePoint.getChargingStartedAt(), Instant.now()).getSeconds());
            chargePoint.setCharging(false);
        }
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
