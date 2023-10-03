package com.control.conversor.mapper;

import com.control.conversor.dto.HealthInsuranceDTO;
import com.control.conversor.dto.UserResponseDTO;
import com.control.conversor.entities.HealthInsurance;
import com.control.conversor.entities.User;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper(componentModel = "spring")
public interface UserMapper {

    @InheritConfiguration
    @Mapping(target = "healthInsurance",ignore = true)
    @Mapping(target = "clientKey",ignore = true)
    UserResponseDTO toUserResponseDTO(User user);

    @Mapping(target = "user",ignore = true)
    List<HealthInsuranceDTO> toHealthInsuranceList(List<HealthInsurance> healthInsurance);

}