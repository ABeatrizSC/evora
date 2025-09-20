package com.github.abeatrizsc.user_service.feign.asaas.dtos;

import java.util.List;

public record AsaasExceptionResponseDto(
        List<AsaasErrorResponseDto> errors
){}