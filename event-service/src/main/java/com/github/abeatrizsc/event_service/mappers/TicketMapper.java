package com.github.abeatrizsc.event_service.mappers;

import com.github.abeatrizsc.event_service.domain.Ticket;
import com.github.abeatrizsc.event_service.dtos.TicketResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface TicketMapper {
    @Mapping(target = "purchasedAt", expression = "java(formatPurchasedAt(ticket.getPurchasedAt()))")
    TicketResponseDto convertEntityToResponseDto(Ticket ticket);

    default String formatPurchasedAt(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }
}
