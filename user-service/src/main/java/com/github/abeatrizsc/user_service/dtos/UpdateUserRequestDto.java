package com.github.abeatrizsc.user_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(
        @NotBlank(message = "Name is required.")
        String nameUpdated,
        @Email(message = "Invalid email format.")
        @NotBlank(message = "Email is required.")
        String emailUpdated,
        @Size(min = 7, max = 8, message = "Password must be 7 to 8 characters.")
        String passwordUpdated,
        @NotBlank(message = "Password is required.")
        String currentPassword)
{}