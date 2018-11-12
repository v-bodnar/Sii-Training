package com.sii.action;

import com.sii.interfaces.LongRunningAction;
import javafx.util.Callback;
import org.jboss.weld.environment.se.contexts.ThreadScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@ThreadScoped
public class LogReader implements LongRunningAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogReader.class);

    @PostConstruct
    private void init() {
        LOGGER.info("Initializing {}", getClass().getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        LOGGER.info("Destroying {}", getClass().getSimpleName());
    }

    @Override
    public void execute(Callback callback) {
        try {
            callback.call(readLogFiles());
        } catch (InterruptedException e) {
            callback.call(e);
        }
    }

    private String readLogFiles() throws InterruptedException {
        LOGGER.info("Gathering logs");
        Thread.sleep(1000);
        return "Some logs";
    }
}
