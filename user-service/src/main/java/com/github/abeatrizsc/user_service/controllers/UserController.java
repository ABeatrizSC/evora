package com.github.abeatrizsc.user_service.controllers;

import com.github.abeatrizsc.user_service.dtos.UpdateUserRequestDto;
import com.github.abeatrizsc.user_service.dtos.UserResponseDto;
import com.github.abeatrizsc.user_service.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PutMapping
    private ResponseEntity<UserResponseDto> updateAuthenticatedUser(@RequestBody @Valid UpdateUserRequestDto updateUserDto) {
        UserResponseDto userResponseDto = service.updateAuthenticatedUser(updateUserDto);

        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping
    private ResponseEntity deleteAuthenticatedUser() {
        service.deleteAuthenticatedUser();

        return ResponseEntity.ok().build();
    }

    @GetMapping
    private ResponseEntity<UserResponseDto> getAuthenticatedUserInfo() {
        UserResponseDto userResponse = service.getAuthenticatedUserInfo();

        return ResponseEntity.ok(userResponse);
    }
}
