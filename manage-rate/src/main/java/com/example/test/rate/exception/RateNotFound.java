package com.example.test.rate.exception;

public class RateNotFound extends RuntimeException{
    public RateNotFound() {
        super();
    }

    public RateNotFound(String message) {
        super(message);
    }

    public RateNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public RateNotFound(Throwable cause) {
        super(cause);
    }

    protected RateNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
