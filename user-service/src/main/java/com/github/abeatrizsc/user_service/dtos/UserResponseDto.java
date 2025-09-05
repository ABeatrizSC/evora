package com.github.abeatrizsc.user_service.dtos;

import com.github.abeatrizsc.user_service.enums.RoleEnum;

public record UserResponseDto(
        String name,
        String email,
        RoleEnum role,
        String customerId)
{}