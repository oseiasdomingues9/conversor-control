package com.control.conversor.controllers;


import com.control.conversor.dto.ClientDTO;
import com.control.conversor.dto.ResponseDTO;
import com.control.conversor.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    private ResponseEntity<ResponseDTO> create(@RequestBody ClientDTO clientDTO) {
        return clientService.create(clientDTO);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ResponseDTO> findById(@PathVariable @Valid String id) {
        return clientService.findById(id);
    }

    @GetMapping("/key/{key}")
    private ResponseEntity<ResponseDTO> findByKey(@PathVariable @Valid String key) {
        return clientService.findByKey(key);
    }

    @GetMapping
    private ResponseEntity<ResponseDTO> findAll() {
        return clientService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@RequestBody @Valid ClientDTO clientDTO, @PathVariable String id){
        return clientService.update(clientDTO,id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable String id){
        return clientService.delete(id);
    }
}
