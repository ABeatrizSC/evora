package com.github.abeatrizsc.event_service.controllers;

import com.github.abeatrizsc.event_service.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService service;
}
