package com.github.abeatrizsc.event_service.specifications;

import com.github.abeatrizsc.event_service.domain.*;
import com.github.abeatrizsc.event_service.enums.EventCategoryEnum;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TicketSpec {
    public static Specification<Ticket> eventTitleContains(String title, String userId) {
        return (root, query, builder) -> {
            if (title == null || title.isBlank()) {
                return null;
            }
            return builder.and(
                    builder.like(root.join(Ticket_.EVENT).get(Event_.TITLE), "%" + title + "%"),
                    builder.equal(root.get(Ticket_.PARTICIPANT_ID), userId)
            );
        };
    }

    public static Specification<Ticket> eventDateEquals(LocalDate date, String userId) {
        return (root, query, builder) -> {
            if (date == null) {
                return null;
            }
            return builder.and(
                    builder.equal(root.join(Ticket_.EVENT).get(Event_.DATE), date),
                    builder.equal(root.get(Ticket_.PARTICIPANT_ID), userId)
            );
        };
    }

    public static Specification<Ticket> eventCategoryEquals(String category, String userId) {
        return (root, query, builder) -> {
            if (category == null || category.isBlank()) {
                return null;
            }
            try {
                EventCategoryEnum categoryEnum = EventCategoryEnum.valueOf(category.toUpperCase());
                return builder.and(
                        builder.equal(root.join(Ticket_.EVENT).get(Event_.CATEGORY), categoryEnum),
                        builder.equal(root.get(Ticket_.PARTICIPANT_ID), userId)
                );
            } catch (IllegalArgumentException e) {
                return null;
            }
        };
    }

    public static Specification<Ticket> ticketPriceLessThanOrEqualTo(BigDecimal price, String userId) {
        return (root, query, builder) -> {
            if (price == null) {
                return null;
            }
            return builder.and(
                    builder.lessThanOrEqualTo(root.join(Ticket_.EVENT).get(Event_.TICKET_PRICE), price),
                    builder.equal(root.get(Ticket_.PARTICIPANT_ID), userId)
            );
        };
    }

    public static Specification<Ticket> purchaseStatusEqual(String purchaseStatus, String userId) {
        return (root, query, builder) -> {
            if (purchaseStatus == null || purchaseStatus.isBlank()) {
                return null;
            }
            return builder.and(
                    builder.equal(root.join(Ticket_.PURCHASE).get(Purchase_.STATUS), purchaseStatus),
                    builder.equal(root.get(Ticket_.PARTICIPANT_ID), userId)
            );
        };
    }

    public static Specification<Ticket> ticketStatus(Boolean isCancelled, String userId) {
        return (root, query, builder) -> {
            if (isCancelled == null) {
                return null;
            }
            var purchaseJoin = root.join(Ticket_.PURCHASE);

            return builder.and(
                    isCancelled
                            ? builder.isNotNull(purchaseJoin.get(Purchase_.CANCELLED_AT))
                            : builder.isNull(purchaseJoin.get(Purchase_.CANCELLED_AT)),
                    builder.equal(root.get(Ticket_.PARTICIPANT_ID), userId)
            );
        };
    }
}