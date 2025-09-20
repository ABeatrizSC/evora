package com.github.abeatrizsc.user_service.feign.asaas.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) //ignore the others fields
public record CreateCustomerResponseDto(
        String id)
{}
