package com.github.abeatrizsc.user_service.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abeatrizsc.user_service.exceptions.AsaasServiceClientException;
import com.github.abeatrizsc.user_service.feign.asaas.dtos.AsaasErrorResponseDto;
import com.github.abeatrizsc.user_service.feign.asaas.dtos.AsaasExceptionResponseDto;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FeignConfig {
    @Value("${api.key}")
    private String apiKey;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new AsaasErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
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
                if (response.body() != null) {
                    AsaasExceptionResponseDto errorResponse =
                            mapper.readValue(response.body().asInputStream(), AsaasExceptionResponseDto.class);

                    AsaasErrorResponseDto error = errorResponse.errors().get(0);

                    log.info("Create and returns a AsaasServiceClientException() successfully.");
                    return new AsaasServiceClientException(error.code(), error.description());
                } else {
                    log.info("Asaas returned status " + response.status() + " with no body");

                    return new RuntimeException();
                }
            } catch (Exception e) {
                log.info("An UNEXPECTED error occurred while trying to create an AsaasServiceClientException: {}", e.getMessage());

                return new RuntimeException("An error ocurred while trying create a customer account. Please, try again.");
            }
        }
    }
}
