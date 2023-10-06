package com.control.conversor.controllers;


import com.control.conversor.dto.ClientDTO;
import com.control.conversor.dto.StatusResponseDTO;
import com.control.conversor.dto.UserDTO;
import com.control.conversor.services.ClientService;
import com.control.conversor.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    private ResponseEntity<StatusResponseDTO> create(@RequestBody ClientDTO clientDTO) {
        return clientService.create(clientDTO);
    }

    @GetMapping("/{id}")
    private ResponseEntity<StatusResponseDTO> findById(@PathVariable @Valid String id) {
        return clientService.findById(id);
    }

    @GetMapping("/key/{key}")
    private ResponseEntity<StatusResponseDTO> findByKey(@PathVariable @Valid String key) {
        return clientService.findByKey(key);
    }

    @GetMapping
    private ResponseEntity<StatusResponseDTO> findAll() {
        return clientService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> update(@RequestBody @Valid ClientDTO clientDTO, @PathVariable String id){
        return clientService.update(clientDTO,id);
    }

    @PutMapping



    @DeleteMapping("/{id}")
    public ResponseEntity<StatusResponseDTO> delete(@PathVariable String id){
        return clientService.delete(id);
    }
}
