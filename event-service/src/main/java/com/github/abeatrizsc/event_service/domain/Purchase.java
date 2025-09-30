package com.github.abeatrizsc.event_service.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.abeatrizsc.event_service.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "purchases")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.PENDING;

    @Column(nullable = false)
    private BigDecimal amount = BigDecimal.valueOf(0.0);

    @Column(nullable = false, name = "due_date")
    private LocalDate dueDate;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @OneToOne(mappedBy = "purchase", cascade = CascadeType.ALL)
    @JsonBackReference
    private Ticket ticket;
}