package com.control.panel.conversor.dto;


import com.control.panel.conversor.enums.PlanType;
import com.control.panel.conversor.enums.UserRole;

public record UserDTO(String login, String password, String email,UserRole role, boolean active, String clientKey,
                      PlanType planType,String url) {
}
