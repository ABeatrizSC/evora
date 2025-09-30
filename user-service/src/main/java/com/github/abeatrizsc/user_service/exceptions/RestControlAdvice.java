package com.github.abeatrizsc.user_service.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.github.abeatrizsc.user_service.dtos.RestErrorMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestControlAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessageDto> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessageDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, errors.get(0)));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RestErrorMessageDto> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessageDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<RestErrorMessageDto> handleAuthException(AuthException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessageDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RestErrorMessageDto> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessageDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Invalid login credentials."));
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<RestErrorMessageDto> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessageDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<RestErrorMessageDto> handleJWTCreationException(JWTCreationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while creating the token."));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<RestErrorMessageDto> handleSecurityException(SecurityException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RestErrorMessageDto(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "You don't have permission to perform this action."));
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<RestErrorMessageDto> handleEmailAlreadyInUseException(EmailAlreadyInUseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new RestErrorMessageDto(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, e.getMessage()));
    }

    @ExceptionHandler(ParticipantFieldsException.class)
    public ResponseEntity<RestErrorMessageDto> handleParticipantFieldsException(ParticipantFieldsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessageDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<RestErrorMessageDto> handleWrongPasswordException(WrongPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessageDto(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<RestErrorMessageDto> handleRequestException(RequestException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessageDto> handleGeneric(Exception e) {
        log.error(String.valueOf(e));
        log.error("Generic handler caught exception: {}", e.getClass().getName(), e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again."));
    }

    @ExceptionHandler(AsaasServiceClientException.class)
    public ResponseEntity<RestErrorMessageDto> handleAsaasServiceClientException(AsaasServiceClientException e) {
        HttpStatus status;

        try {
            status = HttpStatus.valueOf(e.getStatus());
        } catch (Exception ex) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        RestErrorMessageDto body = new RestErrorMessageDto(
                status.value(),
                status,
                e.getDescription()
        );

        return ResponseEntity.status(status).body(body);
    }
}
