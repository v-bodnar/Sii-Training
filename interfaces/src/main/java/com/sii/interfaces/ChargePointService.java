package com.sii.interfaces;

public interface ChargePointService {
    void startCharging();
    void stopCharging();
    boolean isCharging();
    void getLogs();
}
