package com.github.abeatrizsc.event_service.domain;

import com.github.abeatrizsc.event_service.enums.EventCategoryEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "events")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "creator_id", nullable = false)
    private String creatorId;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime hour;

    @Column(nullable = false)
    private LocalTime duration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCategoryEnum category;

    @Column(nullable = false)
    private Integer capacity = 0;

    @Column(name = "ticket_price", nullable = false)
    private BigDecimal ticketPrice = BigDecimal.valueOf(0.0);

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "address_number", nullable = false)
    private String addressNumber;

    @Column(name = "address_complement")
    private String addressComplement;
}