package com.github.abeatrizsc.event_service.feign.asaas;

import com.github.abeatrizsc.event_service.configs.AsaasFeignConfig;
import com.github.abeatrizsc.event_service.feign.asaas.dtos.CreateChargeRequestDto;
import com.github.abeatrizsc.event_service.feign.asaas.dtos.CreateChargeResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        url= "https://api-sandbox.asaas.com",
        name = "asaas",
        configuration = AsaasFeignConfig.class
)
public interface AsaasServiceClient {
    @PostMapping("/v3/payments")
    CreateChargeResponseDto createCharge(@RequestBody CreateChargeRequestDto dto);

    @PostMapping("/v3/payments/{id}/refund")
    void refundCharge(@PathVariable String id);
}
