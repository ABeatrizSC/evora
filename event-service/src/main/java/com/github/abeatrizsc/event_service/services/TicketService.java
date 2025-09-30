package com.github.abeatrizsc.event_service.services;

import com.github.abeatrizsc.event_service.domain.Event;
import com.github.abeatrizsc.event_service.domain.Ticket;
import com.github.abeatrizsc.event_service.dtos.TicketResponseDto;
import com.github.abeatrizsc.event_service.enums.RoleEnum;
import com.github.abeatrizsc.event_service.exceptions.CancellationTicketPurchaseException;
import com.github.abeatrizsc.event_service.exceptions.NotFoundException;
import com.github.abeatrizsc.event_service.exceptions.UnauthorizedActionException;
import com.github.abeatrizsc.event_service.feign.asaas.dtos.CreateChargeResponseDto;
import com.github.abeatrizsc.event_service.feign.asaas.dtos.PurchaseResponseDto;
import com.github.abeatrizsc.event_service.feign.userService.UserServiceClient;
import com.github.abeatrizsc.event_service.feign.userService.dtos.UserResponseDto;
import com.github.abeatrizsc.event_service.mappers.TicketMapper;
import com.github.abeatrizsc.event_service.repositories.TicketRepository;
import com.github.abeatrizsc.event_service.specifications.queryFilters.TicketQueryFilter;
import com.github.abeatrizsc.event_service.utils.AuthRequestUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository repository;
    private final EventService eventService;
    private final PurchaseService purchaseService;
    private final AuthRequestUtils authRequestUtils;
    private final UserServiceClient userClient;
    private final TicketMapper ticketMapper;

    @Transactional
    public CreateChargeResponseDto createEventTicketPurchase(String eventId) {
        log.info("createEventTicketPurchase() started with id {}", eventId);

        checkUserRole();

        Event event = eventService.getEventById(eventId);
        UserResponseDto authenticatedUser = userClient.getAuthenticatedUserInfo();

        PurchaseResponseDto purchaseResponse = null;

        if (event.getTicketPrice().compareTo(BigDecimal.ZERO) > 0) {
            purchaseResponse = purchaseService.createTicketPurchase(
                    event.getTicketPrice(),
                    authenticatedUser.customerId()
            );
        }

        Ticket newTicket = new Ticket();
        newTicket.setParticipantId(authenticatedUser.id());
        newTicket.setEvent(event);
        newTicket.setPurchase(purchaseResponse != null ? purchaseResponse.purchase() : null);
        newTicket.setCode(generateUniqueCode());

        repository.save(newTicket);

        log.info("createEventTicketPurchase() - ticket saved");

        return purchaseResponse != null ? purchaseResponse.chargeResponseDto() : null;
    }

    @Transactional
    public String cancelEventTicketPurchase(String ticketId) {
        log.info("cancelEventTicketPurchase() started with ticket id {}", ticketId);

        checkUserRole();

        Ticket ticket = getTicketByIdAndParticipantId(ticketId);

        if (LocalDate.now().isBefore(ticket.getEvent().getDate())) {
            purchaseService.cancelTicketPurchase(ticket.getPurchase().getCode());
            //update event vacancies

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
        log.info("Generating ticket unique code");

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

    public Page<TicketResponseDto> getAllTicketsByAuthenticatedUser(TicketQueryFilter ticketFilter) {
        Pageable pageable = PageRequest.of(ticketFilter.getPage(), ticketFilter.getItems());
        String userId = authRequestUtils.getAuthenticatedUserId();
        ticketFilter.setUserId(userId);

        return repository
                .findAll(ticketFilter.toEspecification(), pageable)
                .map(ticketMapper::convertEntityToResponseDto);
    }
}