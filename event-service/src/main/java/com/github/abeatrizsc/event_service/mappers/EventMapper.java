package com.github.abeatrizsc.event_service.mappers;

import com.github.abeatrizsc.event_service.domain.Event;
import com.github.abeatrizsc.event_service.dtos.EventRequestDto;
import com.github.abeatrizsc.event_service.dtos.EventResponseDto;
import com.github.abeatrizsc.event_service.enums.EventCategoryEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface EventMapper {
    @Mapping(target = "category", expression = "java(mapCategory(dto.category()))")
    Event convertRequestDtoToEntity(EventRequestDto dto);

    @Mapping(target = "date", expression = "java(formatDate(event.getDate()))")
    EventResponseDto convertEntityToRequestDto(Event event);

    default EventCategoryEnum mapCategory(String category) {
        return EventCategoryEnum.valueOf(category.toUpperCase());
    }

    default String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }
}