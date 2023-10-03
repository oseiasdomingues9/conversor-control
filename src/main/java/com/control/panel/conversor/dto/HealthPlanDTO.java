package com.control.panel.conversor.dto;

import com.control.panel.conversor.enums.PlanType;

import java.util.List;

public record HealthPlanDTO(String id, String name, List<String> version, PlanType planType) {
}
