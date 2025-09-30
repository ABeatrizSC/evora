package com.github.abeatrizsc.event_service.feign.userService;

import com.github.abeatrizsc.event_service.configs.UserFeignConfig;
import com.github.abeatrizsc.event_service.feign.userService.dtos.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "api-gateway",
        path = "/api/v1/users",
        configuration = UserFeignConfig.class
)
public interface UserServiceClient {
    @GetMapping
    UserResponseDto getAuthenticatedUserInfo();
}