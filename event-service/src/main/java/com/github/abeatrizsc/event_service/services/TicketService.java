package com.github.abeatrizsc.event_service.services;

import com.github.abeatrizsc.event_service.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository repository;
}
