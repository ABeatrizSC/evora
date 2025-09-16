package com.github.abeatrizsc.user_service.services;

import com.github.abeatrizsc.user_service.domain.User;
import com.github.abeatrizsc.user_service.dtos.LoginRequestDto;
import com.github.abeatrizsc.user_service.dtos.RegisterRequestDto;
import com.github.abeatrizsc.user_service.enums.RoleEnum;
import com.github.abeatrizsc.user_service.exceptions.EmailAlreadyInUseException;
import com.github.abeatrizsc.user_service.exceptions.ParticipantFieldsException;
import com.github.abeatrizsc.user_service.security.CustomUserDetails;
import com.github.abeatrizsc.user_service.security.SecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final SecurityConfig securityConfig;
    private final UserService userService;

    @Transactional
    public void register(RegisterRequestDto body) {
        Optional<User> user = userService.findUserByEmail(body.email());
        RoleEnum role = RoleEnum.valueOf(body.role().toUpperCase());

        if(user.isEmpty()) {
            if (role == RoleEnum.PARTICIPANT && (body.document() == null || body.mobilePhone() == null)) {
                throw new ParticipantFieldsException();
            }

            User newUser = User.builder()
                    .name(body.name())
                    .email(body.email())
                    .password(securityConfig.passwordEncoder().encode(body.password()))
                    .role(role)
                    .build();

            userService.saveUser(newUser);
        } else {
            throw new EmailAlreadyInUseException();
        }
    }

    public String login(LoginRequestDto body) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.email(), body.password())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return tokenService.generateToken(userDetails);
    }
}
