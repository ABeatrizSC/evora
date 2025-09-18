package com.github.abeatrizsc.event_service.controllers;

import com.github.abeatrizsc.event_service.domain.Event;
import com.github.abeatrizsc.event_service.dtos.EventRequestDto;
import com.github.abeatrizsc.event_service.dtos.EventResponseDto;
import com.github.abeatrizsc.event_service.dtos.EventUpdateRequestDto;
import com.github.abeatrizsc.event_service.services.EventService;
import com.github.abeatrizsc.event_service.specifications.queryFilters.EventQueryFilter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
public class EventController {
    private final EventService service;

    @PostMapping
    public ResponseEntity<List<EventResponseDto>> createEvent(@Valid @RequestBody EventRequestDto body){
        return ResponseEntity.ok(service.createEvent(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<EventResponseDto>> updateEventById(@PathVariable String id, @Valid @RequestBody EventUpdateRequestDto body) {
        return ResponseEntity.ok(service.updateEventById(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<EventResponseDto>> deleteEventById(@PathVariable String id) {
        return ResponseEntity.ok(service.deleteEventById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EventResponseDto>> getAllEvents(EventQueryFilter filter) {
        return ResponseEntity.ok(service.getAllEvents(filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id) {
        return ResponseEntity.ok(service.getEventById(id));
    }
}