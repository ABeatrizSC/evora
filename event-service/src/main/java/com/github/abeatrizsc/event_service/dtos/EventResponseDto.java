package com.github.abeatrizsc.event_service.dtos;

import com.github.abeatrizsc.event_service.enums.EventCategoryEnum;

import java.math.BigDecimal;
import java.time.LocalTime;

public record EventResponseDto (
        String id,
        String title,
        String description,
        String date,
        LocalTime hour,
        EventCategoryEnum category,
        BigDecimal ticketPrice,
        AddressResponseDto address)
{}
