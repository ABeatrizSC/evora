package com.github.abeatrizsc.event_service.dtos;

import com.github.abeatrizsc.event_service.domain.Purchase;

public record TicketResponseDto(
        String id,
        String code,
        String status,
        String purchasedAt,
        EventResponseDto event,
        Purchase purchase
) {}
