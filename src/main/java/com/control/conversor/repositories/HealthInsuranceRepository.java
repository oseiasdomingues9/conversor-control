package com.control.conversor.repositories;

import com.control.conversor.entities.HealthInsurance;
import com.control.conversor.enums.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthInsuranceRepository extends JpaRepository<HealthInsurance,String> {

    List<HealthInsurance> findByPlanType(PlanType planType);
}
