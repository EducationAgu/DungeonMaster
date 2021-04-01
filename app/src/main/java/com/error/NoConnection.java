package com.error;

public class NoConnection extends RuntimeException {

    private String message;

    public NoConnection(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
