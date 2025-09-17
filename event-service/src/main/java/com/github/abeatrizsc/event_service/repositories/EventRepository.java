package com.github.abeatrizsc.event_service.repositories;

import com.github.abeatrizsc.event_service.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    List<Event> findAllByCreatorId(String creatorId);
    Optional<Event> findByIdAndCreatorId(String eventId, String creatorId);
}