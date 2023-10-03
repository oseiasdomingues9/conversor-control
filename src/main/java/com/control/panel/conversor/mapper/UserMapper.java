package com.control.panel.conversor.mapper;

import com.control.panel.conversor.dto.ClientDTO;
import com.control.panel.conversor.dto.HealthPlanDTO;
import com.control.panel.conversor.dto.UserResponseDTO;
import com.control.panel.conversor.entities.Client;
import com.control.panel.conversor.entities.HealthPlan;
import com.control.panel.conversor.entities.User;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper(componentModel = "spring")
public interface UserMapper {

    @InheritConfiguration
    @Mapping(target = "healthPlan",ignore = true)
    @Mapping(target = "clientKey",ignore = true)
    UserResponseDTO toUserResponseDTO(User user);


    @Mapping(target = "id",ignore = true)
    HealthPlan toHealthPlan(HealthPlanDTO healthPlanDTO);

    @Mapping(target = "id",ignore = true)
    Client toClient(ClientDTO clientDTO);
    @Mapping(target = "user",ignore = true)
    List<HealthPlanDTO> toHealthPlanList(List<HealthPlan> healthPlan);

}