package com.github.abeatrizsc.event_service.mappers;

import com.github.abeatrizsc.event_service.domain.Ticket;
import com.github.abeatrizsc.event_service.dtos.TicketResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface TicketMapper {
    TicketResponseDto convertEntityToResponseDto(Ticket ticket);
}
