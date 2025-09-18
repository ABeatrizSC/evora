package com.github.abeatrizsc.event_service.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String object) {
        super(object + " not found");
    }
}
