package com.github.abeatrizsc.event_service.repositories;

import com.github.abeatrizsc.event_service.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {
}
