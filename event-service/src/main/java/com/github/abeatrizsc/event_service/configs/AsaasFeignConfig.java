package com.github.abeatrizsc.event_service.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abeatrizsc.event_service.exceptions.AsaasServiceClientException;
import com.github.abeatrizsc.event_service.exceptions.NotFoundException;
import com.github.abeatrizsc.event_service.feign.asaas.dtos.AsaasErrorResponseDto;
import com.github.abeatrizsc.event_service.feign.asaas.dtos.AsaasExceptionResponseDto;
import com.github.abeatrizsc.event_service.utils.AuthRequestUtils;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AsaasFeignConfig {
    @Value("${api.key}")
    private String apiKey;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new AsaasErrorDecoder();
    }

    @Bean
    public RequestInterceptor asaasRequestInterceptor() {
        return template -> {
            template.header("access_token", apiKey);
            template.header("Content-Type", "application/json");
        };
    }

    public static class AsaasErrorDecoder implements ErrorDecoder {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public Exception decode(String methodKey, Response response) {
            try {
                AsaasExceptionResponseDto errorResponse =
                        mapper.readValue(response.body().asInputStream(), AsaasExceptionResponseDto.class);

                AsaasErrorResponseDto error = errorResponse.errors().get(0);

                log.info("Create and returns a AsaasServiceClientException() successfully.");
                return new AsaasServiceClientException(response.status(), error.description());

            } catch (Exception e) {
                log.info("An UNEXPECTED error occurred while trying to create an AsaasServiceClientException: {}", e.getMessage());

                log.info("Asaas returned {}", response.status(), " with no body.");

                if (response.status() == 404) {
                    throw new NotFoundException("Resource");
                }

                return new RuntimeException();
            }
        }
    }
}
