package com.sii.model;

import java.time.Instant;

public class ChargePoint {
    private boolean charging;
    private Instant chargingStartedAt;

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    public Instant getChargingStartedAt() {
        return chargingStartedAt;
    }

    public void setChargingStartedAt(Instant chargingStartedAt) {
        this.chargingStartedAt = chargingStartedAt;
    }
}
