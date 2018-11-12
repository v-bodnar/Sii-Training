package com.sii.observers;

import com.sii.events.ChargingStartedEvent;
import com.sii.service.GenericChargePointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;

public class ChargingStartedObserver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericChargePointService.class);

    public void onChargingStarted(@Observes ChargingStartedEvent chargingStartedEvent){
        LOGGER.info("Charging started observer");
    }
}
