package com.control.conversor.dto;

import com.control.conversor.enums.PlanType;

import java.util.List;

public record HealthPlanDTO(String id, String name, List<String> version, PlanType planType) {
}
