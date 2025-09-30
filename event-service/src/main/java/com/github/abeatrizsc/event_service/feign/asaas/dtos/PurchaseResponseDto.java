package com.github.abeatrizsc.event_service.feign.asaas.dtos;

import com.github.abeatrizsc.event_service.domain.Purchase;

public record PurchaseResponseDto(
        Purchase purchase,
        CreateChargeResponseDto chargeResponseDto
) {}
