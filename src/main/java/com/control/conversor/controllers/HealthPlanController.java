package com.control.conversor.controllers;


import com.control.conversor.dto.StatusResponseDTO;
import com.control.conversor.services.HealthPlanService;
import com.control.conversor.dto.HealthPlanDTO;
import com.control.conversor.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class HealthPlanController {

    private final HealthPlanService healthPlanService;

    @Autowired
    protected UserMapper userMapper;

    @GetMapping("/{id}")
    private ResponseEntity<StatusResponseDTO> findById(@PathVariable @Valid String id) {
        return healthPlanService.findById(id);
    }

    @GetMapping
    private ResponseEntity<StatusResponseDTO> findAll() {
        return healthPlanService.findAll();
    }

    @PostMapping
    private ResponseEntity<StatusResponseDTO> create(@RequestBody HealthPlanDTO healthPlanDTO){
        return healthPlanService.create(healthPlanDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> delete(@PathVariable String id){
        return healthPlanService.delete(id);
    }
}
