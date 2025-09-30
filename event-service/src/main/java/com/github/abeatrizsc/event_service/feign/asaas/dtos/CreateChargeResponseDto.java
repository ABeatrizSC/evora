package com.github.abeatrizsc.event_service.feign.asaas.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true) //ignore the others fields
public record CreateChargeResponseDto(
        String id,
        BigDecimal value,
        BigDecimal netValue,
        String invoiceUrl,
        String status
) {}