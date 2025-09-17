package com.github.abeatrizsc.event_service.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record EventRequestDto(
        @NotBlank(message = "Title is required")
        @Size(max = 150, message = "Title must be at most 150 characters long")
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 255, message = "Description must be at most 255 characters long")
        String description,

        @NotNull(message = "Date is required")
        @FutureOrPresent(message = "Date must be today or in the future")
        LocalDate date,

        @NotNull(message = "Hour is required")
        LocalTime hour,

        @NotNull(message = "Duration is required")
        LocalTime duration,

        @NotBlank(message = "Category is required")
        String category,

        @NotNull(message = "Capacity is required")
        @Positive(message = "Capacity must be greater than zero")
        Integer capacity,

        @NotNull(message = "Ticket price is required")
        @DecimalMin(value = "0.0", inclusive = true, message = "Ticket price must be greater than or equal to zero")
        BigDecimal ticketPrice,

        @NotBlank(message = "Postal code is required")
        String postalCode,

        @NotBlank(message = "Address number is required")
        String addressNumber,

        @Size(max = 50, message = "Address complement must be at most 50 characters long")
        String addressComplement
) {}