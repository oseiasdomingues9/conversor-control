package com.control.conversor.mapper;

import com.control.conversor.dto.HealthInsuranceDTO;
import com.control.conversor.entities.HealthInsurance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper(componentModel = "spring")
public interface HealthInsuranceMapper {

    @Mapping(target = "id",ignore = true)
    HealthInsurance toHealthInsurance(HealthInsuranceDTO healthInsuranceDTO);

    @Mapping(target = "user",ignore = true)
    List<HealthInsuranceDTO> toHealthInsuranceDTOList(List<HealthInsurance> healthInsurance);

    HealthInsuranceDTO toHealthInsuranceDTO(HealthInsurance healthInsurance);
}