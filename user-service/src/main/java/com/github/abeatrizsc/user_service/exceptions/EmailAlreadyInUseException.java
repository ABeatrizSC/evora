package com.github.abeatrizsc.user_service.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super("Email already in use.");
    }
}
