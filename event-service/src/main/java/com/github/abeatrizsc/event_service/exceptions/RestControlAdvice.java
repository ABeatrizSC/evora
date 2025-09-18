package com.github.abeatrizsc.event_service.exceptions;

import com.github.abeatrizsc.event_service.dtos.RestErrorMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestErrorMessageDto> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestErrorMessageDto(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, e.getMessage()));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<RestErrorMessageDto> handleSecurityException(SecurityException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RestErrorMessageDto(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, "You don't have permission to perform this action."));
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<RestErrorMessageDto> handleUnauthorizedTicketPurchaseException(UnauthorizedActionException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RestErrorMessageDto(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(CancellationTicketPurchaseException.class)
    public ResponseEntity<RestErrorMessageDto> handleCancellationTicketPurchaseException(CancellationTicketPurchaseException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new RestErrorMessageDto(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<RestErrorMessageDto> handleRequestException(RequestException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

   @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessageDto> handleGeneric(Exception e) {
        log.error(String.valueOf(e));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessageDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again."));
    }
}
