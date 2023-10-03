package com.control.conversor.repositories;

import com.control.conversor.entities.HealthPlan;
import com.control.conversor.enums.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthPlanRepository extends JpaRepository<HealthPlan,String> {

    List<HealthPlan> findByPlanType(PlanType planType);
}
