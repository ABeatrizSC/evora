package com.github.abeatrizsc.event_service.dtos;

public record TicketResponseDto(
        String id,
        String code,
        String status,
        String purchasedAt,
        EventResponseDto event
) {}
