package com.github.abeatrizsc.event_service.utils;

import com.github.abeatrizsc.event_service.exceptions.RequestException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class AuthRequestUtils {
    private final HttpServletRequest request;

    public String getAuthenticatedUserId() {
        String userId = request.getHeader("X-User-Id");

        if (userId == null) {
            log.info("getAuthenticatedUserId(): Header 'X-User-Id' is missing.");

            throw new RequestException();
        }

        return userId;
    }

    public String getAuthenticatedUserRole() {
        String userRole = request.getHeader("X-User-Role");
        log.info("Request user role: {}", userRole);

        if (userRole == null) {
            log.info("getAuthenticatedUserId(): Header 'X-User-Role' is missing.");

            throw new RequestException();
        }

        return userRole;
    }

    public Boolean isRequestFromCreator(String creatorId) {
        String requestUserId = getAuthenticatedUserId();

        return Objects.equals(requestUserId, creatorId);
    }
}
