package com.github.abeatrizsc.event_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EventUpdateRequestDto (
        @NotBlank(message = "Title is required")
        @Size(max = 150, message = "Title must be at most 150 characters long")
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 255, message = "Description must be at most 255 characters long")
        String description,

        @NotBlank(message = "Address number is required")
        String addressNumber,

        @Size(max = 50, message = "Address complement must be at most 50 characters long")
        String addressComplement)
{}
