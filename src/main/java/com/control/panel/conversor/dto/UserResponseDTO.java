package com.control.panel.conversor.dto;

import com.control.panel.conversor.enums.PlanType;
import lombok.Data;

import java.util.List;


@Data
public class UserResponseDTO{
    private String id;
    private String login;
    private String email;
    private boolean active;
    private String role;
    private String clientKey;
    private PlanType planType;
    private List<HealthPlanDTO> healthPlan;
    private String url;
}
