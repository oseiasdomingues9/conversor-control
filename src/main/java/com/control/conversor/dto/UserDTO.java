package com.control.conversor.dto;


import com.control.conversor.enums.PlanType;
import com.control.conversor.enums.UserRole;

public record UserDTO(String login, String password, String email, UserRole role, boolean active, String clientKey,
                      PlanType planType, String url) {
}
