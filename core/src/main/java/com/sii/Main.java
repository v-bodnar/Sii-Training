package com.sii;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws InterruptedException {
        LOGGER.info("Starting up");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();

        SeContainer seContainer = initializer
                .disableDiscovery()
                .addPackages(true, Package.getPackage("com.sii"))
                .initialize();

        ChargePointService chargePointService = seContainer.select(ChargePointService.class).get();
        chargePointService.startCharging();
        Thread.sleep(2000);
        chargePointService.stopCharging();

        chargePointService.startCharging();
        Thread.sleep(1000);


        seContainer.close();
        Thread.sleep(1000);
    }
}
