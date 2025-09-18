package com.github.abeatrizsc.event_service.services;

import com.github.abeatrizsc.event_service.domain.Event;
import com.github.abeatrizsc.event_service.domain.Ticket;
import com.github.abeatrizsc.event_service.dtos.TicketResponseDto;
import com.github.abeatrizsc.event_service.enums.RoleEnum;
import com.github.abeatrizsc.event_service.enums.StatusEnum;
import com.github.abeatrizsc.event_service.exceptions.CancellationTicketPurchaseException;
import com.github.abeatrizsc.event_service.exceptions.NotFoundException;
import com.github.abeatrizsc.event_service.exceptions.UnauthorizedActionException;
import com.github.abeatrizsc.event_service.mappers.TicketMapper;
import com.github.abeatrizsc.event_service.repositories.TicketRepository;
import com.github.abeatrizsc.event_service.utils.AuthRequestUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository repository;
    private final EventService eventService;
    private final AuthRequestUtils authRequestUtils;
    private final TicketMapper ticketMapper;

    @Transactional
    public String createEventTicketPurchase(String eventId) {
        log.info("createEventTicketPurchase() started with id {}", eventId);

        checkUserRole();

        String participantId = authRequestUtils.getAuthenticatedUserId();

        Event event = eventService.getEventById(eventId);

        Ticket newTicket = new Ticket();
        newTicket.setParticipantId(participantId);
        newTicket.setEvent(event);
        newTicket.setPurchasedAt(LocalDateTime.now());
        newTicket.setCode(generateUniqueCode());

        repository.save(newTicket);

        log.info("createEventTicketPurchase() ended");

        return "Asaas payment link";
    }

    @Transactional
    public String cancelEventTicketPurchase(String ticketId) {
        log.info("cancelEventTicketPurchase() started with ticket id {}", ticketId);

        checkUserRole();

        Ticket ticket = getTicketByIdAndParticipantId(ticketId);

        if (LocalDate.now().isBefore(ticket.getEvent().getDate()) && ticket.getStatus() != StatusEnum.CANCELLED) {
            //refund
            //update event vacancies
            //set LocalDateTime cancelledAt
            ticket.setStatus(StatusEnum.CANCELLED);
            repository.save(ticket);

            log.info("cancelEventTicketPurchase() ended");
            return "Ticket id: " + ticketId + " cancelled successfully";
        }

        throw new CancellationTicketPurchaseException();
    }

    public void checkUserRole() {
        log.info("checkUserRole() started");

        RoleEnum authenticatedUserRole = RoleEnum.valueOf(authRequestUtils.getAuthenticatedUserRole().toUpperCase());

        if (authenticatedUserRole != RoleEnum.PARTICIPANT) {
            throw new UnauthorizedActionException(
                    String.format("User with role %s is not authorized for this action.", authenticatedUserRole)
            );
        }

        log.info("Success checkUserRole");
    }

    public String generateUniqueCode() {
        String uniqueCode;

        do {
            uniqueCode = RandomStringUtils.randomAlphanumeric(10).toUpperCase();
        } while (repository.findByCode(uniqueCode).isPresent());

       return uniqueCode;
    }

    public Ticket getTicketByIdAndParticipantId(String ticketId) {
        String participantId = authRequestUtils.getAuthenticatedUserId();

        return repository.findByIdAndParticipantId(ticketId, participantId).orElseThrow(() -> new NotFoundException("Ticket"));
    }

    public Page<TicketResponseDto> getAllTicketsByAuthenticatedUser(int page, int items) {
        String participantId = authRequestUtils.getAuthenticatedUserId();
        Pageable pageable = PageRequest.of(page, items);

        return repository
                .findAllByParticipantId(participantId, pageable)
                .map(ticketMapper::convertEntityToResponseDto);
    }
}
