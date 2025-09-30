package com.github.abeatrizsc.event_service.feign.asaas.dtos;

import java.util.List;

public record AsaasExceptionResponseDto(
        List<AsaasErrorResponseDto> errors
){}