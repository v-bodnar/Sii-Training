package com.sii.model;

public abstract class AbstractRequest {
    private String uuid;

    public String getType(){
        return  this.getClass().getSimpleName();
    }

    public String getUuid(){
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
