package com.sii.model;

import com.sii.annotations.Length;

public class AnnotatedRequest extends AbstractRequest {
    @Length(maxSize = 5)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
