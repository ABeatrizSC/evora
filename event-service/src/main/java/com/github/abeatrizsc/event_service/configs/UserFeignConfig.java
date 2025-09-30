package com.github.abeatrizsc.event_service.configs;

import com.github.abeatrizsc.event_service.utils.AuthRequestUtils;
import feign.RequestInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class UserFeignConfig {
    private final AuthRequestUtils authRequestUtils;

    @Bean
    public RequestInterceptor userRequestInterceptor() {
        return template -> {
            String token = authRequestUtils.getAuthorizationToken();
            template.header("Authorization", token);
        };
    }
}
