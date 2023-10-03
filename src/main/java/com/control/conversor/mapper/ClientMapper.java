package com.control.conversor.mapper;

import com.control.conversor.dto.ClientDTO;
import com.control.conversor.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id",ignore = true)
    Client toClient(ClientDTO clientDTO);

    ClientDTO toClientDTO(Client client);

    List<ClientDTO> toClientDTOList(List<Client> clientList);

}