package com.sii.model;

public abstract class AbstractResponse {
    private Status status;

    public abstract String getUuid();

    public abstract void setUuid(String uuid);

    public String getType(){
        return  this.getClass().getSimpleName();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status{
        Success, Error
    }
}
