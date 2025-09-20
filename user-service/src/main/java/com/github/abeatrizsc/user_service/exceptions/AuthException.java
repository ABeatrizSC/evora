package com.github.abeatrizsc.user_service.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {
    public AuthException() {
        super("User not found.");
    }
}
