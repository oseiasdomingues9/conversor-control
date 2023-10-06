package com.control.conversor.services;


import com.control.conversor.dto.ClientDTO;
import com.control.conversor.dto.StatusResponseDTO;
import com.control.conversor.entities.Client;
import com.control.conversor.mapper.ClientMapper;
import com.control.conversor.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;


    @Autowired
    private ClientMapper clientMapper;


    public ResponseEntity<StatusResponseDTO> create(ClientDTO clientDTO) {
        Client client = clientRepository.save(clientMapper.toClient(clientDTO));
        return new ResponseEntity<>(new StatusResponseDTO("Cliente salvo com sucesso", client ,false), HttpStatus.OK);
    }

    public ResponseEntity<StatusResponseDTO> findById(String id){
        Optional<Client> clientOptional = clientRepository.findById(id);
        return clientOptional.map(client -> new ResponseEntity<>(new StatusResponseDTO("Client encontrado com sucesso", clientMapper.toClientDTO(client), false), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new StatusResponseDTO("Cliente com id " + id + " não encontrado", null, true), HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<StatusResponseDTO> findByKey(String key) {
        Optional<Client> clientOptional = clientRepository.findByKey(key);
        return clientOptional.map(client -> new ResponseEntity<>(new StatusResponseDTO("Client encontrado com sucesso", clientMapper.toClientDTO(client), false), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(new StatusResponseDTO("Cliente com key " + key + " não encontrado", null, true), HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<StatusResponseDTO> findAll(){
        List<Client> clientList = clientRepository.findAll();
        return new ResponseEntity<>(new StatusResponseDTO("Cliente salvo com sucesso", clientMapper.toClientDTOList(clientList) ,false), HttpStatus.OK);
    }

    public ResponseEntity<StatusResponseDTO> update(ClientDTO clientDTO, String id) {
        Optional<Client> opt = clientRepository.findById(id);
        if(opt.isPresent()) {
            Client client = opt.get();
            client.setName(clientDTO.name());
            client.setKey(clientDTO.key());
            clientRepository.save(client);
            return new ResponseEntity<>(new StatusResponseDTO("Usuario Atualizado com sucesso",client, false), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new StatusResponseDTO("Usuario não encontrado",null, true), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<StatusResponseDTO> delete(String id){
        clientRepository.deleteById(id);
        return new ResponseEntity<>(new StatusResponseDTO("Cliente deletado com sucesso",null,false),HttpStatus.OK);
    }


}
