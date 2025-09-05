package com.github.abeatrizsc.user_service.controllers;

import com.github.abeatrizsc.user_service.dtos.LoginRequestDto;
import com.github.abeatrizsc.user_service.dtos.LoginResponseDto;
import com.github.abeatrizsc.user_service.dtos.RegisterRequestDto;
import com.github.abeatrizsc.user_service.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto body){
        String token = authService.login(body);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequestDto body) {
        authService.register(body);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
