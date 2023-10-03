package com.control.conversor.services;

import com.control.conversor.dto.StatusResponseDTO;
import com.control.conversor.dto.UserDTO;
import com.control.conversor.dto.UserResponseDTO;
import com.control.conversor.entities.Client;
import com.control.conversor.entities.HealthInsurance;
import com.control.conversor.enums.PlanType;
import com.control.conversor.repositories.ClientRepository;
import com.control.conversor.repositories.HealthInsuranceRepository;
import com.control.conversor.repositories.UserRepository;
import com.control.conversor.entities.User;
import com.control.conversor.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HealthInsuranceRepository healthInsuranceRepository;
    private final ClientRepository clientRepository;

    @Autowired
    protected UserMapper userMapper;

    public ResponseEntity<StatusResponseDTO> create(UserDTO userDTO){
        if(userRepository.findByLogin(userDTO.login()) != null){
            return new ResponseEntity<>(new StatusResponseDTO("Usuario já existe",null, true), HttpStatus.BAD_REQUEST);
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());

        Client client = clientRepository.findByKey(userDTO.clientKey());
        User user = new User(userDTO.login(),encryptedPassword, userDTO.email(), userDTO.role(), userDTO.active(),client, userDTO.planType(),userDTO.url());
        User newUser = userRepository.save(user);

        return new ResponseEntity<>(new StatusResponseDTO("Usuario criado com sucesso",newUser,false),HttpStatus.OK);
    }

    public ResponseEntity<StatusResponseDTO> findById(String id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            List<HealthInsurance> healthInsurances = healthInsuranceRepository.findByPlanType(user.getPlanType());
            UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(user);
            userResponseDTO.setHealthInsurance(userMapper.toHealthInsuranceList(healthInsurances));
            userResponseDTO.setClientKey(user.getClient().getKey());
            return new ResponseEntity<>(new StatusResponseDTO("Usuario encontrado com sucesso", userResponseDTO ,false), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new StatusResponseDTO("Usuario com id " + id + " não encontrado", null ,true), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<StatusResponseDTO> findAll(){
        List<User> userList = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        for (User user : userList) {
            List<HealthInsurance> healthInsurances = healthInsuranceRepository.findByPlanType(user.getPlanType());
            UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(user);
            userResponseDTO.setHealthInsurance(userMapper.toHealthInsuranceList(healthInsurances));
            userResponseDTO.setClientKey(user.getClient() != null ? user.getClient().getKey() : null);
            userResponseDTOS.add(userResponseDTO);
        }
        return new ResponseEntity<>(new StatusResponseDTO("Usuarios encontrado com sucesso", userResponseDTOS ,false), HttpStatus.OK);
    }


    public ResponseEntity<StatusResponseDTO> update(UserDTO userDTO, String id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()) {
            User user = opt.get();
            String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());

            user.setLogin(userDTO.login());
            user.setPassword(encryptedPassword);
            user.setEmail(userDTO.email());
            user.setRole(userDTO.role());
            user.setActive(userDTO.active());
            Client client = clientRepository.findByKey(userDTO.clientKey());
            user.setClient(client);
            user.setPlanType(userDTO.planType());
            user.setUrl(userDTO.url());
            userRepository.save(user);
            return new ResponseEntity<>(new StatusResponseDTO("Usuario Atualizado com sucesso",user, false), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new StatusResponseDTO("Usuario não encontrado",null, true), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<StatusResponseDTO> delete(String id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(new StatusResponseDTO("Usuario deletado com sucesso",null,false),HttpStatus.OK);
    }

    public ResponseEntity<List<String>> findPlans() {
        List<String> plansList = Arrays.stream(PlanType.values())
                .map(planType -> StringUtils.capitalize(planType.name().toLowerCase()))
                .toList();
        return new ResponseEntity<>(plansList,HttpStatus.OK);
    }
}
