package com.github.abeatrizsc.event_service.feign.userService.dtos;

import com.github.abeatrizsc.event_service.enums.RoleEnum;

public record UserResponseDto (
        String id,
        String name,
        String email,
        RoleEnum role,
        String customerId)
{}