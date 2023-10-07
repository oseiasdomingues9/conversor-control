package com.control.conversor.controllers;


import com.control.conversor.dto.HealthInsuranceDTO;
import com.control.conversor.dto.ResponseDTO;
import com.control.conversor.services.HealthInsuranceService;
import com.control.conversor.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health-insurance")
@RequiredArgsConstructor
public class HealthInsuranceController {

    private final HealthInsuranceService healthInsuranceService;

    @Autowired
    protected UserMapper userMapper;

    @GetMapping("/{id}")
    private ResponseEntity<ResponseDTO> findById(@PathVariable @Valid String id) {
        return healthInsuranceService.findById(id);
    }

    @GetMapping
    private ResponseEntity<ResponseDTO> findAll() {
        return healthInsuranceService.findAll();
    }

    @PostMapping
    private ResponseEntity<ResponseDTO> create(@RequestBody HealthInsuranceDTO healthInsuranceDTO){
        return healthInsuranceService.create(healthInsuranceDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@RequestBody @Valid HealthInsuranceDTO healthInsuranceDTO, @PathVariable String id){
        return healthInsuranceService.update(healthInsuranceDTO,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable String id){
        return healthInsuranceService.delete(id);
    }
}
