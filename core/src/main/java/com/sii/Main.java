package com.sii;

import com.sii.interfaces.ChargePointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();


    public static void main(String... args) throws InterruptedException {
        LOGGER.info("Starting up");
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();

        SeContainer seContainer = initializer
                .disableDiscovery()
                .addPackages(true, Package.getPackage("com.sii"))
                .initialize();

        ChargePointService chargePointService = seContainer.select(ChargePointService.class).get();

//      TODO thread scoped call
//        chargePointService.isCharging();
//        executorService.submit(() -> chargePointService.getLogs());
//        executorService.submit(() -> chargePointService.getLogs());
//        executorService.submit(() -> chargePointService.getLogs());
//
//        Thread.sleep(2000);

//      TODO multi threaded charging
//        executorService.submit(() -> chargePointService.startCharging());
//        executorService.submit(() -> chargePointService.startCharging());
//        executorService.submit(() -> chargePointService.startCharging());
//
//        Thread.sleep(2000);

//        TODO simple test
        chargePointService.startCharging();
        Thread.sleep(2000);
        chargePointService.stopCharging();

        chargePointService.startCharging();
        Thread.sleep(1000);


        seContainer.close();
        Thread.sleep(1000);
    }
}
