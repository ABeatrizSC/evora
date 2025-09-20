package com.github.abeatrizsc.user_service.feign.asaas.dtos;

public record CustomerRequestDto(
        String name,
        String cpfCnpj,
        String mobilePhone)
{}
