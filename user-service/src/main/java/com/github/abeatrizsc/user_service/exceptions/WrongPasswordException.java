package com.github.abeatrizsc.user_service.exceptions;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Incorrect password.");
    }
}
