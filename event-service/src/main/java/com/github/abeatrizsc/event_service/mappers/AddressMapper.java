package com.github.abeatrizsc.event_service.mappers;

import com.github.abeatrizsc.event_service.domain.Address;
import com.github.abeatrizsc.event_service.dtos.AddressResponseDto;
import com.github.abeatrizsc.event_service.dtos.ViaCepResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(source = "cep", target = "postalCode")
    @Mapping(source = "logradouro", target = "street")
    @Mapping(source = "bairro", target = "province")
    @Mapping(source = "localidade", target = "city")
    @Mapping(source = "uf", target = "state")
    Address convertViaCepResponseDtoToEntity(ViaCepResponseDto dto);

    AddressResponseDto convertEntityToResponseDto(Address address);
}
