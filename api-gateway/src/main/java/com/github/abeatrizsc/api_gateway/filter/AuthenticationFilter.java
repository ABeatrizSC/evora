package com.github.abeatrizsc.api_gateway.filter;

import com.auth0.jwt.interfaces.Claim;
import com.github.abeatrizsc.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RouteValidator validator;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

                if (authHeaders == null || authHeaders.isEmpty()) {
                    throw new SecurityException("To access this resource, you must be authenticated.");
                }

                String authHeader = authHeaders.get(0);
                if (authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                String userId = jwtUtil.validateAndGetUserId(authHeader);

                Map<String, Claim> tokenClaims = jwtUtil.getTokenClaims(authHeader);

                if (userId == null) {
                    throw new SecurityException("Invalid or expired token.");
                }

                exchange = exchange.mutate()
                        .request(builder -> builder
                                .header("X-User-Id", userId)
                                .header("X-User-Role", tokenClaims.get("role").asString().replace("ROLE_", "")))
                        .build();

            }

            return chain.filter(exchange);
        };
    }

    public static class Config {}
}
