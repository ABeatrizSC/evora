package com.github.abeatrizsc.user_service.controllers;

import com.github.abeatrizsc.user_service.dtos.UpdateUserRequestDto;
import com.github.abeatrizsc.user_service.dtos.UserResponseDto;
import com.github.abeatrizsc.user_service.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PutMapping
    private ResponseEntity<UserResponseDto> updateAuthenticatedUser(HttpServletRequest request, @RequestBody @Valid UpdateUserRequestDto updateUserDto) {
        UserResponseDto userResponseDto = service.updateAuthenticatedUser(request, updateUserDto);

        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping
    private ResponseEntity deleteAuthenticatedUser(HttpServletRequest request) {
        service.deleteAuthenticatedUser(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    private ResponseEntity<UserResponseDto> getAuthenticatedUserInfo(HttpServletRequest request) {
        UserResponseDto userResponse = service.getAuthenticatedUserInfo(request);

        return ResponseEntity.ok(userResponse);
    }
}
