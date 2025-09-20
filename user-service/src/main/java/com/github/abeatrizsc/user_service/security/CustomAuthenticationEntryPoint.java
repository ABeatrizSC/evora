package com.github.abeatrizsc.user_service.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException) throws IOException {

        log.info("AuthenticationException");

        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType("application/json");

        Map<String, Object> body = Map.of(
                "status", 404,
                "error", "NOT_FOUND",
                "message", "User not found."
        );

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}