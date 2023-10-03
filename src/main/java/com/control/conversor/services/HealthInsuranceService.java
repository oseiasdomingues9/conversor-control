package com.control.conversor.services;


import com.control.conversor.dto.HealthInsuranceDTO;
import com.control.conversor.entities.HealthInsurance;
import com.control.conversor.mapper.HealthInsuranceMapper;
import com.control.conversor.repositories.HealthInsuranceRepository;
import com.control.conversor.dto.StatusResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HealthInsuranceService {

    private final HealthInsuranceRepository healthInsuranceRepository;

    @Autowired
    private HealthInsuranceMapper healthInsuranceMapper;


    public ResponseEntity<StatusResponseDTO> create(HealthInsuranceDTO healthInsuranceDTO) {
        HealthInsurance healthInsurance = healthInsuranceRepository.save(healthInsuranceMapper.toHealthInsurance(healthInsuranceDTO));
        return new ResponseEntity<>(new StatusResponseDTO("Operadora salva com sucesso", healthInsurance,false), HttpStatus.OK);
    }

    public ResponseEntity<StatusResponseDTO> findById(String id){
        Optional<HealthInsurance> healthPlanOptional = healthInsuranceRepository.findById(id);
        return healthPlanOptional.map(client -> new ResponseEntity<>(new StatusResponseDTO("Operadora encontrada com sucesso", healthInsuranceMapper.toHealthInsuranceDTO(healthPlanOptional.get()), false), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new StatusResponseDTO("Operadora com id " + id + " não encontrado", null, true), HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<StatusResponseDTO> findAll(){
        List<HealthInsurance> healthInsuranceList = healthInsuranceRepository.findAll();
        return new ResponseEntity<>(new StatusResponseDTO("Operadora salva com sucesso", healthInsuranceMapper.toHealthInsuranceDTOList(healthInsuranceList) ,false), HttpStatus.OK);
    }

    public ResponseEntity<StatusResponseDTO> update(HealthInsuranceDTO healthInsuranceDTO, String id) {
        Optional<HealthInsurance> opt = healthInsuranceRepository.findById(id);
        if(opt.isPresent()) {
            HealthInsurance healthInsurance = opt.get();
            healthInsurance.setName(healthInsuranceDTO.name());
            healthInsurance.setVersion(healthInsuranceDTO.version());
            healthInsurance.setPlanType(healthInsuranceDTO.planType());
            healthInsuranceRepository.save(healthInsurance);
            return new ResponseEntity<>(new StatusResponseDTO("Operadora Atualizada com sucesso", healthInsurance, false), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new StatusResponseDTO("Operadora não encontrado",null, true), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<StatusResponseDTO> delete(String id){
        healthInsuranceRepository.deleteById(id);
        return new ResponseEntity<>(new StatusResponseDTO("Operadora deletada com sucesso",null,false),HttpStatus.OK);
    }

}
