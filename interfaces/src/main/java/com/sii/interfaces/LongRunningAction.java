package com.sii.interfaces;

import javafx.util.Callback;

public interface LongRunningAction {
    void execute(Callback callback);
}
