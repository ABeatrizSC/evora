package com.github.abeatrizsc.user_service.exceptions;

import lombok.Getter;

@Getter
public class AsaasServiceClientException extends RuntimeException {
    private final String code;
    private final String description;

    public AsaasServiceClientException(String code, String description) {
        super(description);
        this.code = code;
        this.description = description;
    }
}
