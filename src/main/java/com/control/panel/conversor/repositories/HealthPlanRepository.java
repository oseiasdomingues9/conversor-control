package com.control.panel.conversor.repositories;

import com.control.panel.conversor.entities.HealthPlan;
import com.control.panel.conversor.entities.User;
import com.control.panel.conversor.enums.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface HealthPlanRepository extends JpaRepository<HealthPlan,String> {

    List<HealthPlan> findByPlanType(PlanType planType);
}
