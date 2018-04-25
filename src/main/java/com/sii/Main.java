package com.sii;

import com.sii.model.AbstractResponse;
import com.sii.model.AnnotatedRequest;
import com.sii.transport.Controller;
import com.sii.transport.MockServer;
import com.sii.transport.ResponseCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args){
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);

        MockServer.getInstance().startListening();
        Controller.getInstance().startListening();

        MockServer.getInstance().setServerState(MockServer.ServerState.NORMAL);

        //Show the order, unpredictable
        //Show time, reflection cache
        //Show exception, tell about future returned
        for (int i = 0; i < 2; i++) {
            AnnotatedRequest annotatedRequest = new AnnotatedRequest();
            //annotatedRequest.setMessage("test");
            annotatedRequest.setMessage("Some message");
            annotatedRequest.setResponseCallback(new ResponseCallback() {
                @Override
                public void onResponse(AbstractResponse response) {
                    LOGGER.info("Doing some heavy work!!!");
                }
            });
            LOGGER.info("Sending message #{}", i + 1);
            Controller.getInstance().sendMessage(annotatedRequest);
        }

//        MockServer.getInstance().shutdown();
//        Controller.getInstance().shutdown();
    }

}
