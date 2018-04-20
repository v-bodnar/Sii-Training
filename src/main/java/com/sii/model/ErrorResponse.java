package com.sii.model;

public class ErrorResponse extends AbstractResponse {

    public ErrorResponse(){
        this.setStatus(Status.Error);
    }

    @Override
    public String getUuid() {
        return null;
    }

    @Override
    public void setUuid(String uuid) {

    }
}
