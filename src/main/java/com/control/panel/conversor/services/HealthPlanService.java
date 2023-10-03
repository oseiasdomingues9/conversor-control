package com.control.panel.conversor.services;


import com.control.panel.conversor.dto.ClientDTO;
import com.control.panel.conversor.dto.HealthPlanDTO;
import com.control.panel.conversor.dto.StatusResponseDTO;
import com.control.panel.conversor.entities.Client;
import com.control.panel.conversor.entities.HealthPlan;
import com.control.panel.conversor.mapper.ClientMapper;
import com.control.panel.conversor.mapper.HealthPlanMapper;
import com.control.panel.conversor.repositories.ClientRepository;
import com.control.panel.conversor.repositories.HealthPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HealthPlanService {

    private final HealthPlanRepository healthPlanRepository;

    @Autowired
    private HealthPlanMapper healthPlanMapper;


    public ResponseEntity<StatusResponseDTO> create(HealthPlanDTO healthPlanDTO) {
        HealthPlan healthPlan= healthPlanRepository.save(healthPlanMapper.toHealthPlan(healthPlanDTO));
        return new ResponseEntity<>(new StatusResponseDTO("Plano salvo com sucesso", healthPlan ,false), HttpStatus.OK);
    }

    public ResponseEntity<StatusResponseDTO> findById(String id){
        Optional<HealthPlan> healthPlanOptional = healthPlanRepository.findById(id);
        return healthPlanOptional.map(client -> new ResponseEntity<>(new StatusResponseDTO("Plano encontrado com sucesso", healthPlanMapper.toHealthPlanDTO(healthPlanOptional.get()), false), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new StatusResponseDTO("Plano com id " + id + " não encontrado", null, true), HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<StatusResponseDTO> findAll(){
        List<HealthPlan> healthPlanList = healthPlanRepository.findAll();
        return new ResponseEntity<>(new StatusResponseDTO("Plano salvo com sucesso", healthPlanMapper.toHealthPlanDTOList(healthPlanList) ,false), HttpStatus.OK);
    }

    public ResponseEntity<StatusResponseDTO> update(HealthPlanDTO healthPlanDTO, String id) {
        Optional<HealthPlan> opt = healthPlanRepository.findById(id);
        if(opt.isPresent()) {
            HealthPlan healthPlan = opt.get();
            healthPlan.setName(healthPlanDTO.name());
            healthPlan.setVersion(healthPlanDTO.version());
            healthPlan.setPlanType(healthPlanDTO.planType());
            healthPlanRepository.save(healthPlan);
            return new ResponseEntity<>(new StatusResponseDTO("Plano Atualizado com sucesso",healthPlan, false), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new StatusResponseDTO("Plano não encontrado",null, true), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<StatusResponseDTO> delete(String id){
        healthPlanRepository.deleteById(id);
        return new ResponseEntity<>(new StatusResponseDTO("Plano deletado com sucesso",null,false),HttpStatus.OK);
    }

}
