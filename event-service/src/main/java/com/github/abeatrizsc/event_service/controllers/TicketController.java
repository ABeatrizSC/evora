package com.github.abeatrizsc.event_service.controllers;

import com.github.abeatrizsc.event_service.dtos.TicketResponseDto;
import com.github.abeatrizsc.event_service.feign.asaas.dtos.CreateChargeResponseDto;
import com.github.abeatrizsc.event_service.services.TicketService;
import com.github.abeatrizsc.event_service.specifications.queryFilters.TicketQueryFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService service;

    @PostMapping
    public ResponseEntity<CreateChargeResponseDto> createEventTicketPurchase(@RequestParam("event") String eventId){
        return ResponseEntity.ok(service.createEventTicketPurchase(eventId));
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<String> cancelEventTicketPurchase(@PathVariable String ticketId){
        return ResponseEntity.ok(service.cancelEventTicketPurchase(ticketId));
    }

    @GetMapping
    public ResponseEntity<Page<TicketResponseDto>> getAllTicketsByAuthenticatedUser(TicketQueryFilter filter) {
        return ResponseEntity.ok(service.getAllTicketsByAuthenticatedUser(filter));
    }
}