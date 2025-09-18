package com.github.abeatrizsc.api_gateway.filter;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    public static final Map<String, List<HttpMethod>> openApiEndpoints = Map.of(
            "/api/v1/auth/register", List.of(HttpMethod.POST),
            "/api/v1/auth/login", List.of(HttpMethod.POST),
            "/eureka", List.of(HttpMethod.GET),
            "/api/v1/events", List.of(HttpMethod.GET),
            "/api/v1/events/*", List.of(HttpMethod.GET)
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints.entrySet().stream().noneMatch(entry -> {
                String uriPattern = entry.getKey(); //open route
                List<HttpMethod> allowedMethods = entry.getValue(); //open route allowed methods

                //if the request URI matches with the open route and allowed methods contain the request method
                return pathMatcher.match(uriPattern, request.getURI().getPath())
                        && allowedMethods.contains(request.getMethod());
            });
}