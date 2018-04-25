package com.sii.transport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sii.exceptions.ConnectionTimeout;
import com.sii.model.AbstractRequest;
import com.sii.model.AbstractResponse;
import com.sii.model.AnnotatedRequest;
import com.sii.model.ErrorResponse;
import com.sii.model.UndefinedResponse;
import com.sii.model.UnifiedResponse;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class MockServer {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MockServer.class);
    private ExecutorService executorService = Executors.newCachedThreadPool(r -> {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setName("MockServer-" + thread.getName());
        return thread;
    });

    private BlockingDeque<AbstractResponse> messageQueue = new LinkedBlockingDeque<>();
    private ServerState serverState = ServerState.NORMAL;

    public void onMessageReceived(String json) {
        executorService.submit(() -> {

            LOGGER.info("Message received" + json);
            try {
                if (ServerState.ALWAYS_ERROR.equals(serverState)) {
                    constructResponse(json, AbstractResponse.Status.Error)
                            .ifPresent(messageQueue::add);
                } else if(ServerState.ALWAYS_EXCEPTION.equals(serverState)) {
                    throw new ConnectionTimeout();
                }else {
                    constructResponse(json, AbstractResponse.Status.Success)
                            .ifPresent(messageQueue::add);
                }
            } catch (IOException e) {
                messageQueue.add(new ErrorResponse());
            }
        });
    }

    private Optional<AbstractResponse> constructResponse(String json, AbstractResponse.Status status) throws IOException {
        Optional<String> type = Optional.ofNullable(JsonParser.getObjectMapper().readValue(json, ObjectNode.class).get("type"))
                .map(JsonNode::asText);

        Optional<String> uuid = Optional.ofNullable(JsonParser.getObjectMapper().readValue(json, ObjectNode.class).get("uuid"))
                .map(JsonNode::asText);


        if (type.isPresent()) {
            Optional<? extends AbstractRequest> request = getRequestClass(type.get())
                    .flatMap(clazz -> JsonParser.toObject(json, clazz));

            return request.map(request1 -> new UnifiedResponse(
                    request1.getUuid(),
                    status));
        } else {
            return uuid.map(UndefinedResponse::new);
        }
    }

    public static Optional<Class<? extends AbstractResponse>> getResponseClass(String response) {
        Optional<String> type;
        try {
            type = Optional.ofNullable(JsonParser.getObjectMapper().readValue(response, ObjectNode.class).get("type"))
                    .map(JsonNode::asText);
        } catch (IOException e) {
            LOGGER.error("Wrong JSON format, no type identifier");
            return Optional.empty();
        }

        if(type.isPresent()) {
            switch (type.get()) {
                case "UnifiedResponse":
                    return Optional.of(UnifiedResponse.class);
                case "UndefinedResponse":
                    return Optional.of(UndefinedResponse.class);
                case "ErrorResponse":
                    return Optional.of(UndefinedResponse.class);
                default:
                    LOGGER.warn("Unknown request type");
                    return Optional.empty();
            }
        }else {
            return Optional.empty();
        }
    }


    private Optional<Class<? extends AbstractRequest>> getRequestClass(String type) {
        switch (type) {
            case "AnnotatedRequest":
                return Optional.of(AnnotatedRequest.class);
            default:
                LOGGER.warn("Unknown request type");
                return Optional.empty();
        }
    }



    public void startListening() {
        executorService.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    AbstractResponse message = messageQueue.take();
                    JsonParser.toJson(message)
                            .ifPresent(s -> Controller.getInstance().onResponse(s));
                }
            } catch (InterruptedException e) {
                LOGGER.warn("Mock Server was stopped");
            }
        });
    }

    public void shutdown() {
        executorService.shutdownNow();
    }

    private static final MockServer INSTANCE = new MockServer();

    public ServerState getServerState() {
        return serverState;
    }

    public void setServerState(ServerState serverState) {
        this.serverState = serverState;
    }

    private MockServer() {
    }

    public static MockServer getInstance() {
        return INSTANCE;
    }

    public enum ServerState {
        ALWAYS_ERROR, NORMAL, ALWAYS_EXCEPTION
    }
}
