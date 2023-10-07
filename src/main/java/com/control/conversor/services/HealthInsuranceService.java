package com.control.conversor.services;


import com.control.conversor.dto.HealthInsuranceDTO;
import com.control.conversor.entities.HealthInsurance;
import com.control.conversor.exception.ResourceNotFoundException;
import com.control.conversor.mapper.HealthInsuranceMapper;
import com.control.conversor.repositories.HealthInsuranceRepository;
import com.control.conversor.dto.ResponseDTO;
import com.control.conversor.utils.MessageUtils;
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


    public ResponseEntity<ResponseDTO> create(HealthInsuranceDTO healthInsuranceDTO) {
        HealthInsurance healthInsurance = healthInsuranceRepository.save(healthInsuranceMapper.toHealthInsurance(healthInsuranceDTO));
        return new ResponseEntity<>(MessageUtils.successMessage("Operadora salva com sucesso",healthInsurance),HttpStatus.OK);
    }

    public ResponseEntity<ResponseDTO> findById(String id){
        Optional<HealthInsurance> healthPlanOptional = healthInsuranceRepository.findById(id);
        return healthPlanOptional.map(healthInsurance -> new ResponseEntity<>(MessageUtils.successMessage("Operadora encontrada com sucesso",healthInsuranceMapper.toHealthInsuranceDTO(healthInsurance)), HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Operadora com id " + id + " não encontrado"));
    }

    public ResponseEntity<ResponseDTO> findAll(){
        List<HealthInsurance> healthInsuranceList = healthInsuranceRepository.findAll();
        return new ResponseEntity<>(MessageUtils.successMessage("Operadoras encontradas com sucesso",healthInsuranceMapper.toHealthInsuranceDTOList(healthInsuranceList)),HttpStatus.OK);
    }

    public ResponseEntity<ResponseDTO> update(HealthInsuranceDTO healthInsuranceDTO, String id) {
        Optional<HealthInsurance> opt = healthInsuranceRepository.findById(id);
        if(opt.isPresent()) {
            HealthInsurance healthInsurance = opt.get();
            healthInsurance.setName(healthInsuranceDTO.name());
            healthInsurance.setVersion(healthInsuranceDTO.version());
            healthInsurance.setPlanType(healthInsuranceDTO.planType());
            healthInsuranceRepository.save(healthInsurance);
            return new ResponseEntity<>(MessageUtils.successMessage("Operadora Atualizada com sucesso",healthInsurance),HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException("Operadora não encontrado");
        }
    }

    public ResponseEntity<ResponseDTO> delete(String id){
        healthInsuranceRepository.deleteById(id);
        return new ResponseEntity<>(MessageUtils.successMessage("Operadora deletada com sucesso",null),HttpStatus.OK);
    }

}
