package com.github.abeatrizsc.event_service.exceptions;

import lombok.Getter;

@Getter
public class AsaasServiceClientException extends RuntimeException {
    private final int status;
    private final String description;

    public AsaasServiceClientException(int status, String description) {
        super(description);
        this.status = status;
        this.description = description;
    }
}