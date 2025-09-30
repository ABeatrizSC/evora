package com.github.abeatrizsc.event_service.repositories;

import com.github.abeatrizsc.event_service.domain.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String>, JpaSpecificationExecutor<Ticket> {
    Page<Ticket> findAll(Specification<Ticket> spec, Pageable pageable);
    Optional<Ticket> findByIdAndParticipantId(String ticketId, String participantId);
    Optional<Ticket> findByCode(String code);
}