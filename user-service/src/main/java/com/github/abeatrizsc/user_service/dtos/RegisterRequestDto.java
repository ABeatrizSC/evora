package com.github.abeatrizsc.user_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank(message = "Name is required.")
        String name,

        @Email(message = "Invalid email format.")
        @NotBlank(message = "Email is required.")
        String email,

        @NotBlank(message = "Password is required.")
        @Size(min = 7, max = 8, message = "Password must be 7 to 8 characters long and include at least one number.")
        String password,

        @NotBlank(message = "User role is required.")
        String role,

        @Pattern(regexp = "^[0-9]{11}$|^[0-9]{14}$", message = "Invalid document.")
        String document,
        @Pattern(regexp = "^[0-9]{10,13}$", message = "Invalid mobile phone.")
        String mobilePhone
) {
}