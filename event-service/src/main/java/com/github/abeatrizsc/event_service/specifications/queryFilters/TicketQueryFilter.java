package com.github.abeatrizsc.event_service.specifications.queryFilters;

import com.github.abeatrizsc.event_service.domain.Ticket;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.github.abeatrizsc.event_service.specifications.TicketSpec.*;

@Data
public class TicketQueryFilter {
    private int page = 0;
    private int items = 15;
    private String userId;
    private String eventTitle;
    private LocalDate eventDate;
    private String eventCategory;
    private BigDecimal ticketPriceUntil;
    private String purchaseStatus;
    private Boolean cancelled;

    public Specification<Ticket> toEspecification() {
        return eventTitleContains(eventTitle, userId)
                .and(eventDateEquals(eventDate, userId))
                .and(eventCategoryEquals(eventCategory, userId))
                .and(ticketPriceLessThanOrEqualTo(ticketPriceUntil, userId))
                .and(purchaseStatusEqual(purchaseStatus, userId))
                .and(ticketStatus(cancelled, userId));
    }
}
