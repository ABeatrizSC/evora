package com.github.abeatrizsc.event_service.exceptions;

public class RequestException extends RuntimeException {
    public RequestException() {
        super("An error occurred during the request and it was not possible to complete it.");
    }
}
