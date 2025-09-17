package com.github.abeatrizsc.event_service.dtos;

import org.springframework.http.HttpStatus;

public record RestErrorMessageDto(
        Integer status,
        HttpStatus error,
        String message)
{}
