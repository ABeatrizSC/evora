package com.github.abeatrizsc.event_service.mappers;

import com.github.abeatrizsc.event_service.domain.Event;
import com.github.abeatrizsc.event_service.dtos.EventRequestDto;
import com.github.abeatrizsc.event_service.enums.EventCategoryEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "category", expression = "java(mapCategory(dto.category()))")
    Event convertRequestDtoToEntity(EventRequestDto dto);

    default EventCategoryEnum mapCategory(String category) {
        return EventCategoryEnum.valueOf(category.toUpperCase());
    }
}