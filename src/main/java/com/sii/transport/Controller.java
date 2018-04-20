package com.sii.transport;

import com.sii.annotations.processor.Validator;
import com.sii.exceptions.ValidationException;
import com.sii.model.AbstractRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    private ExecutorService executorService = Executors.newCachedThreadPool(r -> {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setName("Controller-" + thread.getName());
        return thread;
    });

    private BlockingDeque<AbstractRequest> messageQueue = new LinkedBlockingDeque<>();


    public void sendMessage(AbstractRequest message) throws ValidationException {
        Instant start = Instant.now();
        try {
            Validator.validate(message);
        } catch (ValidationException e) {
            LOGGER.error(e.getMessage(), e);
            return;
        }
        message.setUuid(UUID.randomUUID().toString());
        JsonParser.toJson(message).ifPresent(jsonString -> LOGGER.info("Send message: " + jsonString));
        messageQueue.add(message);
        Duration timeElapsed = Duration.between(start, Instant.now());
        LOGGER.debug("Time taken: " + timeElapsed.toMillis() + " milliseconds");
    }

    public void onResponse(String response) {
        executorService.submit(() -> LOGGER.info("Response received: " + response));
    }

    public void startListening() {
        executorService.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    AbstractRequest message = messageQueue.take();
                    LOGGER.info("Queue has a message to send");
                    JsonParser.toJson(message)
                            .ifPresent(MockServer.getInstance()::onMessageReceived);
                }
            } catch (InterruptedException e) {
                LOGGER.warn("Controller was stopped");
            }
        });
    }

    public void shutdown() {
        executorService.shutdownNow();
    }


    private static final Controller INSTANCE = new Controller();

    private Controller() {
    }

    public static Controller getInstance() {
        return INSTANCE;
    }

}
