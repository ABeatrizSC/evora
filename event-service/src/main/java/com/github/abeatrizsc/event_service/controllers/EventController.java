package com.github.abeatrizsc.event_service.controllers;

import com.github.abeatrizsc.event_service.domain.Event;
import com.github.abeatrizsc.event_service.dtos.EventRequestDto;
import com.github.abeatrizsc.event_service.dtos.EventUpdateRequestDto;
import com.github.abeatrizsc.event_service.services.EventService;
import com.github.abeatrizsc.event_service.specifications.queryFilters.EventQueryFilter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
public class EventController {
    private final EventService service;

    @PostMapping
    public ResponseEntity<List<Event>> createEvent(@Valid @RequestBody EventRequestDto body){
        return ResponseEntity.ok(service.createEvent(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<Event>> updateEventById(@PathVariable String id, @Valid @RequestBody EventUpdateRequestDto body) {
        return ResponseEntity.ok(service.updateEventById(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Event>> deleteEventById(@PathVariable String id) {
        return ResponseEntity.ok(service.deleteEventById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Event>> getAllEvents(EventQueryFilter filter) {
        return ResponseEntity.ok(service.getAllEvents(filter));
    }
}