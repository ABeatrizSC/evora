package com.github.abeatrizsc.event_service.specifications.queryFilters;

import com.github.abeatrizsc.event_service.domain.Event;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.github.abeatrizsc.event_service.specifications.EventSpec.*;

@Data
public class EventQueryFilter {
    private int page = 0;
    private int items = 15;
    private String title;
    private LocalDate date;
    private String category;
    private String city;
    private String state;
    private BigDecimal ticketPriceUntil;

    public Specification<Event> toEspecification() {
        return titleContains(title)
                .and(dateEquals(date))
                .and(categoryEquals(category))
                .and(cityEquals(city))
                .and(stateEquals(state))
                .and(ticketPriceLessThanOrEqualTo(ticketPriceUntil));
    }
}
