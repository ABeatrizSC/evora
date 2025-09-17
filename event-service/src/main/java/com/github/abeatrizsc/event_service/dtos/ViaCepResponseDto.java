package com.github.abeatrizsc.event_service.dtos;

public record ViaCepResponseDto(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf
) {}

