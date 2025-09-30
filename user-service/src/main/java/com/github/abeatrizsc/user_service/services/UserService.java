package com.github.abeatrizsc.user_service.services;

import com.github.abeatrizsc.user_service.domain.User;
import com.github.abeatrizsc.user_service.dtos.UpdateUserRequestDto;
import com.github.abeatrizsc.user_service.dtos.UserResponseDto;
import com.github.abeatrizsc.user_service.enums.RoleEnum;
import com.github.abeatrizsc.user_service.exceptions.AsaasServiceClientException;
import com.github.abeatrizsc.user_service.exceptions.RequestException;
import com.github.abeatrizsc.user_service.exceptions.UserNotFoundException;
import com.github.abeatrizsc.user_service.exceptions.WrongPasswordException;
import com.github.abeatrizsc.user_service.feign.asaas.AsaasServiceClient;
import com.github.abeatrizsc.user_service.feign.asaas.dtos.CustomerRequestDto;
import com.github.abeatrizsc.user_service.repositories.UserRepository;
import com.github.abeatrizsc.user_service.security.SecurityConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final SecurityConfig securityConfig;
    private final HttpServletRequest request;
    private final AsaasServiceClient asaasService;

    public Optional<User> findUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User findUserById(String id) {
        return repository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void saveUser(User user) {
        repository.save(user);
    }

    @Transactional
    public UserResponseDto updateAuthenticatedUser(UpdateUserRequestDto updateDto) {
        User user = findUserById(getAuthenticatedUserId());

        if (isProvidedPasswordCorrect(updateDto.currentPassword(), user.getPassword())) {
            if (areDifferentPasswords(updateDto.passwordUpdated(), user.getPassword())) {
                user.setPassword(securityConfig.passwordEncoder().encode(updateDto.passwordUpdated()));
            }

            user.setName(updateDto.nameUpdated());
            user.setEmail(updateDto.emailUpdated());

            if (user.getRole() == RoleEnum.PARTICIPANT) {
                updateAsaasCustomer(user.getCustomerId(), updateDto);
            }
        }

        repository.save(user);

        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCustomerId());
    }

    @Transactional
    public void deleteAuthenticatedUser() {
        User user = findUserById(getAuthenticatedUserId());

        if (user.getRole() == RoleEnum.PARTICIPANT) {
            deleteAsaasCustomer(user.getCustomerId());
        }

        repository.delete(user);
    }

    public String getAuthenticatedUserId() {
        String userId = request.getHeader("X-User-Id");

        if (userId == null) {
            log.info("User service - getAuthenticatedUserId(): Header 'X-User-Id' is missing.");

            throw new RequestException("An error occurred during the request and it was not possible to complete it.");
        }

        return userId;
    }

    public UserResponseDto getAuthenticatedUserInfo() {
        User user = findUserById(getAuthenticatedUserId());

        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCustomerId());
    }

    public Boolean isProvidedPasswordCorrect(String providedPassword, String userPassword) {
        if(securityConfig.passwordEncoder().matches(providedPassword, userPassword)) {
            return true;
        }

        throw new WrongPasswordException();
    }

    public Boolean areDifferentPasswords(String newPassword, String userPassword){
        if(newPassword == null) {
            return false;
        }

        return !securityConfig.passwordEncoder().matches(newPassword, userPassword);
    }

    public void deleteAsaasCustomer(String id) {
        log.info("deleteAsaasCustomer() started");
        try {
            asaasService.deleteAsaasCustomer(id);
            log.info("deleteAsaasCustomer() ended successfully");
        } catch (AsaasServiceClientException e) {
            log.info("CATCH an AsaasServiceClientException in deleteAsaasCustomer(): {}", e.getMessage());
            throw e;
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        }
    }

    public void updateAsaasCustomer(String customerId, UpdateUserRequestDto updateDto) {
        log.info("updateAsaasCustomer() started");

        CustomerRequestDto customerRequest = new CustomerRequestDto(updateDto.nameUpdated(), updateDto.documentUpdated(), updateDto.mobilePhoneUpdated());

        asaasService.updateAsaasCustomer(customerId, customerRequest);

        log.info("updateAsaasCustomer() ended");
    }
}
