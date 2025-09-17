package com.github.abeatrizsc.event_service.exceptions;

public class UnauthorizedEventCreationException extends RuntimeException {
    public UnauthorizedEventCreationException(String s) {
        super(s);
    }
}