package com.github.abeatrizsc.event_service.controllers;

import com.github.abeatrizsc.event_service.dtos.TicketResponseDto;
import com.github.abeatrizsc.event_service.services.TicketService;
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
@RequestMapping("/api/v1/events")
@AllArgsConstructor
public class TicketController {
    private final TicketService service;

    @PostMapping("/{eventId}/tickets")
    public ResponseEntity<String> createEventTicketPurchase(@PathVariable String eventId){
        return ResponseEntity.ok(service.createEventTicketPurchase(eventId));
    }

    @PutMapping("/tickets/{ticketId}")
    public ResponseEntity<String> cancelEventTicketPurchase(@PathVariable String ticketId){
        return ResponseEntity.ok(service.cancelEventTicketPurchase(ticketId));
    }

    @GetMapping("/tickets")
    public ResponseEntity<Page<TicketResponseDto>> getAllTicketsByAuthenticatedUser(@RequestParam int page, @RequestParam int items) {
        return ResponseEntity.ok(service.getAllTicketsByAuthenticatedUser(page, items));
    }
}
