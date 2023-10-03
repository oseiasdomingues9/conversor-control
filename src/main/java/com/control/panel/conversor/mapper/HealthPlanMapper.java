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
public interface HealthPlanMapper {

    @Mapping(target = "id",ignore = true)
    HealthPlan toHealthPlan(HealthPlanDTO healthPlanDTO);

    @Mapping(target = "user",ignore = true)
    List<HealthPlanDTO> toHealthPlanDTOList(List<HealthPlan> healthPlan);

    HealthPlanDTO toHealthPlanDTO(HealthPlan healthPlan);
}