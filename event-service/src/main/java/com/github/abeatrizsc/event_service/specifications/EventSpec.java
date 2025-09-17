package com.github.abeatrizsc.event_service.specifications;

import com.github.abeatrizsc.event_service.domain.Event;
import com.github.abeatrizsc.event_service.domain.Event_;
import com.github.abeatrizsc.event_service.enums.EventCategoryEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EventSpec {
    public static Specification<Event> titleContains(String title) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(title)) {
                return null;
            }
            return builder.like(root.get(Event_.TITLE), "%" + title + "%");
        };
    }

    public static Specification<Event> dateEquals(LocalDate date) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(date)) {
                return null;
            }
            return builder.equal(root.get(Event_.DATE), date);
        };
    }

    public static Specification<Event> categoryEquals(String category) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(category)) {
                return null;
            }
            return builder.equal(root.get(Event_.CATEGORY), EventCategoryEnum.valueOf(category.toUpperCase()));
        };
    }

    public static Specification<Event> cityEquals(String city) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(city)) {
                return null;
            }
            return builder.equal(root.join(Event_.ADDRESS).get("city"), city);
        };
    }

    public static Specification<Event> stateEquals(String state) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(state)) {
                return null;
            }
            return builder.equal(root.join(Event_.ADDRESS).get("state"), state);
        };
    }

    public static Specification<Event> ticketPriceLessThanOrEqualTo(BigDecimal ticket) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(ticket)) {
                return null;
            }
            return builder.lessThanOrEqualTo(root.get(Event_.TICKET_PRICE), ticket);
        };
    }
}