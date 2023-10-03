package com.control.panel.conversor.controllers;


import com.control.panel.conversor.dto.ClientDTO;
import com.control.panel.conversor.dto.StatusResponseDTO;
import com.control.panel.conversor.entities.Client;
import com.control.panel.conversor.entities.HealthPlan;
import com.control.panel.conversor.enums.PlanType;
import com.control.panel.conversor.enums.UserRole;
import com.control.panel.conversor.mapper.UserMapper;
import com.control.panel.conversor.repositories.ClientRepository;
import com.control.panel.conversor.repositories.HealthPlanRepository;
import com.control.panel.conversor.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Autowired
    protected UserMapper userMapper;


    @GetMapping("/{id}")
    private ResponseEntity<StatusResponseDTO> findById(@PathVariable @Valid String id) {
        return clientService.findById(id);
    }

    @GetMapping
    private ResponseEntity<StatusResponseDTO> findAll() {
        return clientService.findAll();
    }

    @PostMapping
    private ResponseEntity<StatusResponseDTO> create(@RequestBody ClientDTO clientDTO) {
        return clientService.create(clientDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> delete(@PathVariable String id){
        return clientService.delete(id);
    }
}
