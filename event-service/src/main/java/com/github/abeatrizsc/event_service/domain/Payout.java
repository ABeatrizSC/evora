package com.github.abeatrizsc.event_service.domain;

import com.github.abeatrizsc.event_service.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "payouts")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payout {
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

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
