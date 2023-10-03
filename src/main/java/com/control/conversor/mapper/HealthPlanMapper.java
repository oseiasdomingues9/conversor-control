package com.control.conversor.mapper;

import com.control.conversor.dto.HealthPlanDTO;
import com.control.conversor.entities.HealthPlan;
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