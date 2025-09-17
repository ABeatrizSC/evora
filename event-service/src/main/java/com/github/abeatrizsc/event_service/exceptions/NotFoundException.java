package com.github.abeatrizsc.event_service.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String r) {
        super(r + " not found");
    }
}
