package com.github.abeatrizsc.user_service.feign.asaas;

import com.github.abeatrizsc.user_service.configs.FeignConfig;
import com.github.abeatrizsc.user_service.feign.asaas.dtos.CustomerRequestDto;
import com.github.abeatrizsc.user_service.feign.asaas.dtos.CreateCustomerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        url= "https://api-sandbox.asaas.com",
        name = "asaas",
        configuration = FeignConfig.class
)
public interface AsaasServiceClient {
    @PostMapping("/v3/customers")
    CreateCustomerResponseDto createAsaasCustomer(@RequestBody CustomerRequestDto dto);

    @PostMapping("/v3/customers/{id}")
    void deleteAsaasCustomer(@PathVariable String id);

    @PutMapping("/v3/customers/{id}")
    void updateAsaasCustomer(@PathVariable String id, @RequestBody CustomerRequestDto dto);
}
