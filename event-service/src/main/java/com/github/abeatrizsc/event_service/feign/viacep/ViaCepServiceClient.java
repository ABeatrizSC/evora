package com.github.abeatrizsc.event_service.feign.viacep;

import com.github.abeatrizsc.event_service.dtos.ViaCepResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url= "https://viacep.com.br/ws/" , name = "viacep")
public interface ViaCepServiceClient {
    @GetMapping("{cep}/json")
    ViaCepResponseDto findAddressByCep(@PathVariable("cep") String cep);
}
