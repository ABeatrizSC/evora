package com.github.abeatrizsc.event_service.services;

import com.github.abeatrizsc.event_service.domain.Address;
import com.github.abeatrizsc.event_service.domain.Event;
import com.github.abeatrizsc.event_service.dtos.EventRequestDto;
import com.github.abeatrizsc.event_service.dtos.EventResponseDto;
import com.github.abeatrizsc.event_service.dtos.EventUpdateRequestDto;
import com.github.abeatrizsc.event_service.dtos.ViaCepResponseDto;
import com.github.abeatrizsc.event_service.enums.RoleEnum;
import com.github.abeatrizsc.event_service.exceptions.NotFoundException;
import com.github.abeatrizsc.event_service.exceptions.UnauthorizedEventCreationException;
import com.github.abeatrizsc.event_service.feign.ViaCepServiceClient;
import com.github.abeatrizsc.event_service.mappers.AddressMapper;
import com.github.abeatrizsc.event_service.mappers.EventMapper;
import com.github.abeatrizsc.event_service.repositories.AddressRepository;
import com.github.abeatrizsc.event_service.repositories.EventRepository;
import com.github.abeatrizsc.event_service.specifications.queryFilters.EventQueryFilter;
import com.github.abeatrizsc.event_service.utils.AuthRequestUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository repository;
    private final AddressRepository addressRepository;
    private final AuthRequestUtils authRequestUtils;
    private final ViaCepServiceClient cepClient;
    private final EventMapper eventMapper;
    private final AddressMapper addressMapper;

    @Transactional
    public List<EventResponseDto> createEvent(EventRequestDto body) {
        log.info("Create event started");

        String creatorId = authRequestUtils.getAuthenticatedUserId();

        RoleEnum creatorRole = RoleEnum.valueOf(authRequestUtils.getAuthenticatedUserRole().toUpperCase());

        if (creatorRole != RoleEnum.CREATOR) {
            throw new UnauthorizedEventCreationException("Participants cannot create an event.");
        }

        Address address = insertEventAddress(body);

        Event event = eventMapper.convertRequestDtoToEntity(body);
        event.setAddress(address);
        event.setCreatorId(creatorId);

        repository.save(event);

        log.info("Create event ended");
        return getAllByCreatorId(creatorId);
    }

    @Transactional
    public List<EventResponseDto> updateEventById(String eventId, EventUpdateRequestDto updateRequestDto) {
        log.info("Update event by id {} started", eventId);

        String creatorId = authRequestUtils.getAuthenticatedUserId();

        Event event = getEventByIdAndCreatorId(eventId, creatorId);
        event.setTitle(updateRequestDto.title());
        event.setDescription(updateRequestDto.description());
        event.setAddressNumber(updateRequestDto.addressNumber());
        event.setAddressComplement(updateRequestDto.addressComplement());
        repository.save(event);

        log.info("Update event by id {} ended", eventId);
        return getAllByCreatorId(creatorId);
    }

    @Transactional
    public List<EventResponseDto> deleteEventById(String eventId) {
        log.info("Delete event id {} started", eventId);

        String creatorId = authRequestUtils.getAuthenticatedUserId();

        Event event = getEventByIdAndCreatorId(eventId, creatorId);

        repository.delete(event);

        log.info("Delete event ended");

        return getAllByCreatorId(creatorId);
    }

    public Address insertEventAddress(EventRequestDto body) {
        Address address = addressRepository.findByPostalCode(body.postalCode());

        if (address == null) {
            log.info("Address not found for postalCode: {}", body.postalCode());

            ViaCepResponseDto viaCepResponse = cepClient.findAddressByCep(body.postalCode());
            address = addressMapper.convertViaCepResponseDtoToEntity(viaCepResponse);

            log.info("Address created from ViaCep: {}", address);
            address = addressRepository.save(address);
        }

        return address;
    }

    public Event getEventByIdAndCreatorId(String eventId, String creatorId) {
        log.info("Getting an event by id: {} and creator id: {}.", eventId, creatorId);
        return repository.findByIdAndCreatorId(eventId, creatorId).orElseThrow(() -> new NotFoundException("Event"));
    }

    public Page<EventResponseDto> getAllEvents(EventQueryFilter eventFilter) {
        Pageable pageable = PageRequest.of(eventFilter.getPage(), eventFilter.getItems());
        return repository.findAll(eventFilter.toEspecification(), pageable)
                .map(eventMapper::convertEntityToRequestDto);
    }

    public List<EventResponseDto> getAllByCreatorId(String creatorId) {
        return repository.findAllByCreatorId(creatorId)
                .stream()
                .map(eventMapper::convertEntityToRequestDto)
                .toList();
    }

    public Event getEventById(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Event"));
    }
}