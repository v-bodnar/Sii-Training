package com.sii.model;

public class UndefinedResponse extends AbstractResponse {
    private String uuid;

    public UndefinedResponse(){

    }

    public UndefinedResponse(String uuid) {
        this.uuid = uuid;
        this.setStatus(Status.Error);
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