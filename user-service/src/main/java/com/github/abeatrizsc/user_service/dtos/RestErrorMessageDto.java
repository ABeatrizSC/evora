package com.github.abeatrizsc.user_service.dtos;

import org.springframework.http.HttpStatus;

public record RestErrorMessageDto(
        Integer status,
        HttpStatus error,
        String message)
{}
