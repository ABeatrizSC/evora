package com.github.abeatrizsc.user_service.services;

import com.github.abeatrizsc.user_service.domain.User;
import com.github.abeatrizsc.user_service.dtos.LoginRequestDto;
import com.github.abeatrizsc.user_service.dtos.RegisterRequestDto;
import com.github.abeatrizsc.user_service.enums.RoleEnum;
import com.github.abeatrizsc.user_service.exceptions.AsaasServiceClientException;
import com.github.abeatrizsc.user_service.exceptions.EmailAlreadyInUseException;
import com.github.abeatrizsc.user_service.exceptions.ParticipantFieldsException;
import com.github.abeatrizsc.user_service.exceptions.RequestException;
import com.github.abeatrizsc.user_service.feign.asaas.AsaasServiceClient;
import com.github.abeatrizsc.user_service.feign.asaas.dtos.CustomerRequestDto;
import com.github.abeatrizsc.user_service.feign.asaas.dtos.CreateCustomerResponseDto;
import com.github.abeatrizsc.user_service.security.CustomUserDetails;
import com.github.abeatrizsc.user_service.security.SecurityConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final SecurityConfig securityConfig;
    private final UserService userService;
    private final AsaasServiceClient asaasService;

    @Transactional
    public void register(RegisterRequestDto body) {
        log.info("register() started");
        Optional<User> user = userService.findUserByEmail(body.email());
        RoleEnum role = RoleEnum.valueOf(body.role().toUpperCase());
        String customerId = null;

        if(user.isEmpty()) {
            log.info("e-mail is not in use, registering an user with role {}", role);

            if (role == RoleEnum.PARTICIPANT) {
                if(body.document() == null || body.mobilePhone() == null) {
                    throw new ParticipantFieldsException();
                }
                customerId = createAsaasCustomer(body);
            }

            User newUser = User.builder()
                    .name(body.name())
                    .customerId(customerId)
                    .email(body.email())
                    .password(securityConfig.passwordEncoder().encode(body.password()))
                    .role(role)
                    .build();

            userService.saveUser(newUser);
            log.info("register() ended successfully");
        } else {
            throw new EmailAlreadyInUseException();
        }
    }

    public String login(LoginRequestDto body) {
        log.info("User {} login started", body);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.email(), body.password())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("generateToken() started with userDetails: {}", userDetails);
        return tokenService.generateToken(userDetails);
    }

    public String createAsaasCustomer(RegisterRequestDto dto) {
        log.info("createAsaasCustomer() started");
        try {
            CreateCustomerResponseDto responseDto = asaasService.createAsaasCustomer(new CustomerRequestDto(dto.name(), dto.document(), dto.mobilePhone()));

            log.info("createAsaasCustomer() ended successfully");
            return responseDto.id();
        } catch (AsaasServiceClientException e) {
            log.info("CATCH an AsaasServiceClientException in createAsaasCustomer(): {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            throw new RequestException("An error occurred while trying to create a participant account. Try again later.");
        }
    }
}
