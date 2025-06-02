package com.dhh.apiRestSpringboot3.mapper;

import com.dhh.apiRestSpringboot3.dto.ClientDTO;
import com.dhh.apiRestSpringboot3.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    ClientDTO toDTO(Client cliente);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    Client toEntity(ClientDTO clienteDTO);

}