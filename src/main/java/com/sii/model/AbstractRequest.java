package com.sii.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sii.transport.ResponseCallback;

public abstract class AbstractRequest {
    private String uuid;

    @JsonIgnore
    private ResponseCallback responseCallback = new ResponseCallback() {
        @Override
        public void onResponse(AbstractResponse response) {

        }
    };

    public String getType(){
        return  this.getClass().getSimpleName();
    }

    public String getUuid(){
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public synchronized void onResponse(AbstractResponse response){
        responseCallback.accept(response);
    }

    public void setResponseCallback(ResponseCallback responseCallback) {
        this.responseCallback = responseCallback;
    }
}
