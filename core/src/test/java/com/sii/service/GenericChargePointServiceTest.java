package com.sii.service;


import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@EnableWeld
@Ignore
public class GenericChargePointServiceTest {

    @WeldSetup
    private WeldInitiator weld = WeldInitiator
            .from(GenericChargePointService.class, StubPaymentTerminal.class)
            .build();

//    @WeldSetup
//    private WeldInitiator weld = WeldInitiator
//            .from(GenericChargePointService.class, GenericChargePointServiceTest.class)
//            .build();
//
//    @ApplicationScoped
//    @Produces
//    private PaymentTerminal mockPaymentTerminal() {
//        return Mockito.mock(PaymentTerminal.class);
//    }

//    @WeldSetup
//    private WeldInitiator weld = WeldInitiator
//            .from(GenericChargePointService.class)
//            .addBeans(mockPaymentTerminal())
//            .build();

//    static Bean<?> mockPaymentTerminal() {
//        return MockBean.builder()
//                .types(PaymentTerminal.class)
//                .scope(ApplicationScoped.class)
//                .creating(Mockito.mock(PaymentTerminal.class))
//                .build();
//    }


    @Inject
    private GenericChargePointService genericChargePointService;

    @Test
    public void startChargingTest() {
        genericChargePointService.startCharging();
        assertTrue(genericChargePointService.isCharging());
    }

    @Test
    public void stopChargingTest() {
        genericChargePointService.startCharging();
        assertTrue(genericChargePointService.isCharging());
        genericChargePointService.stopCharging();
        assertFalse(genericChargePointService.isCharging());
    }
}
