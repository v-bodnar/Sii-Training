package com.sii.model;

public class UnifiedResponse extends AbstractResponse {
    private String uuid;

    public UnifiedResponse(){}

    public UnifiedResponse(String uuid, Status status) {
        this.setStatus(status);
        this.uuid = uuid;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
