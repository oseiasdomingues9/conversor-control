package com.control.conversor.controllers;


import com.control.conversor.dto.HealthInsuranceDTO;
import com.control.conversor.dto.StatusResponseDTO;
import com.control.conversor.services.HealthInsuranceService;
import com.control.conversor.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health-insurance")
@RequiredArgsConstructor
public class HealthInsuranceController {

    private final HealthInsuranceService healthInsuranceService;

    @Autowired
    protected UserMapper userMapper;

    @GetMapping("/{id}")
    private ResponseEntity<StatusResponseDTO> findById(@PathVariable @Valid String id) {
        return healthInsuranceService.findById(id);
    }

    @GetMapping
    private ResponseEntity<StatusResponseDTO> findAll() {
        return healthInsuranceService.findAll();
    }

    @PostMapping
    private ResponseEntity<StatusResponseDTO> create(@RequestBody HealthInsuranceDTO healthInsuranceDTO){
        return healthInsuranceService.create(healthInsuranceDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> delete(@PathVariable String id){
        return healthInsuranceService.delete(id);
    }
}
