package com.sii.transport;

import com.sii.model.AbstractResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public abstract class ResponseCallback implements Consumer<AbstractResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseCallback.class);
    private AtomicBoolean responseReceived = new AtomicBoolean();

    @Override
    public void accept(AbstractResponse response) {
        //LOGGER.info("Executing response callback: {}", response.getUuid());
        this.responseReceived.set(true);
        this.onResponse(response);
    }

    public abstract void onResponse(AbstractResponse response);

    public boolean isResponseReceived() {
        return responseReceived.get();
    }
}
