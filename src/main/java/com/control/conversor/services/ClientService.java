package com.control.conversor.services;


import com.control.conversor.dto.ClientDTO;
import com.control.conversor.dto.ResponseDTO;
import com.control.conversor.entities.Client;
import com.control.conversor.exception.ResourceNotFoundException;
import com.control.conversor.mapper.ClientMapper;
import com.control.conversor.repositories.ClientRepository;
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
public class ClientService {

    private final ClientRepository clientRepository;


    @Autowired
    private ClientMapper clientMapper;


    public ResponseEntity<ResponseDTO> create(ClientDTO clientDTO) {
        Client client = clientRepository.save(clientMapper.toClient(clientDTO));
        return new ResponseEntity<>(MessageUtils.successMessage("Cliente salvo com sucesso",client),HttpStatus.OK);
    }

    public ResponseEntity<ResponseDTO> findById(String id){
        Optional<Client> clientOptional = clientRepository.findById(id);
        return clientOptional.map(client -> new ResponseEntity<>(MessageUtils.successMessage("Cliente encontrado com sucesso",clientMapper.toClientDTO(client)),HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com id " + id + " não encontrado"));
    }

    public ResponseEntity<ResponseDTO> findByKey(String key) {
        Optional<Client> clientOptional = clientRepository.findByKey(key);
        return clientOptional.map(client -> new ResponseEntity<>(MessageUtils.successMessage("Cliente encontrado com sucesso",clientMapper.toClientDTO(client)),HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com key " + key + " não encontrado"));
    }

    public ResponseEntity<ResponseDTO> findAll(){
        List<Client> clientList = clientRepository.findAll();
        return new ResponseEntity<>(MessageUtils.successMessage("Clientes encontrados com sucesso",clientMapper.toClientDTOList(clientList)),HttpStatus.OK);
    }

    public ResponseEntity<ResponseDTO> update(ClientDTO clientDTO, String id) {
        Optional<Client> opt = clientRepository.findById(id);
        if(opt.isPresent()) {
            Client client = opt.get();
            client.setName(clientDTO.name());
            client.setKey(clientDTO.key());
            clientRepository.save(client);
            return new ResponseEntity<>(MessageUtils.successMessage("Cliente atualizado com sucesso",client),HttpStatus.OK);
        }else {
            throw  new ResourceNotFoundException("Cliente com id " + id + " não encontrado");
        }
    }

    public ResponseEntity<ResponseDTO> delete(String id){
        clientRepository.deleteById(id);
        return new ResponseEntity<>(MessageUtils.successMessage("Cliente deletado com sucesso",null),HttpStatus.OK);
    }


}
