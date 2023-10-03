package com.control.panel.conversor.controllers;

import com.control.panel.conversor.dto.UserDTO;
import com.control.panel.conversor.dto.StatusResponseDTO;
import com.control.panel.conversor.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<StatusResponseDTO> create(@RequestBody @Valid UserDTO userDTO){
        return userService.create(userDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> findById(@PathVariable @Valid String id){
        return userService.findById(id);
    }

    @GetMapping
    public ResponseEntity<StatusResponseDTO> findAll(){
        return userService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> update(@RequestBody @Valid UserDTO userDTO, @PathVariable String id){
        return userService.update(userDTO,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> delete(@PathVariable String id){
        return userService.delete(id);
    }

    @GetMapping("/plans")
    public ResponseEntity<List<String>> findPlans(){
        return userService.findPlans();
    }



}
