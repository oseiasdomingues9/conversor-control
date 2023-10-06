package com.control.conversor.dto;

import com.control.conversor.enums.PlanType;
import lombok.Data;

import java.util.List;


@Data
public class UserResponseDTO{
    private String id;
    private String login;
    private String email;
    private boolean active;
    private String role;
    private ClientDTO client;
    private PlanType planType;
    private List<HealthInsuranceDTO> healthInsurance;
    private String url;
}
