package com.github.abeatrizsc.event_service.feign.asaas.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CreateChargeRequestDto (
        String customer,
        String billingType,
        BigDecimal value,
        LocalDate dueDate
) {}