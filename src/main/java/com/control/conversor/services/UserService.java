package com.control.conversor.services;

import com.control.conversor.dto.PlanDTO;
import com.control.conversor.dto.ResponseDTO;
import com.control.conversor.dto.UserDTO;
import com.control.conversor.dto.UserResponseDTO;
import com.control.conversor.entities.Client;
import com.control.conversor.entities.HealthInsurance;
import com.control.conversor.enums.PlanType;
import com.control.conversor.exception.ResourceAlreadyExistsException;
import com.control.conversor.exception.ResourceNotFoundException;
import com.control.conversor.mapper.ClientMapper;
import com.control.conversor.repositories.ClientRepository;
import com.control.conversor.repositories.HealthInsuranceRepository;
import com.control.conversor.repositories.UserRepository;
import com.control.conversor.entities.User;
import com.control.conversor.mapper.UserMapper;
import com.control.conversor.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HealthInsuranceRepository healthInsuranceRepository;
    private final ClientRepository clientRepository;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected ClientMapper clientMapper;

    public ResponseEntity<ResponseDTO> create(UserDTO userDTO){

        if(userRepository.findByLogin(userDTO.login()) != null) throw new ResourceAlreadyExistsException("Usuário já existe");

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());

        Optional<Client> clientOpt= clientRepository.findByKey(userDTO.clientKey());
        Client client = null;
        if (clientOpt.isPresent()){
            client = clientOpt.get();
        }
        User user = new User(userDTO.login(),encryptedPassword, userDTO.email(), userDTO.role(), userDTO.active(),client, userDTO.planType(),userDTO.url());
        User newUser = userRepository.save(user);

        return new ResponseEntity<>(MessageUtils.successMessage("Usuário criado com sucesso",newUser),HttpStatus.OK);
    }

    public ResponseEntity<ResponseDTO> findById(String id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            List<HealthInsurance> healthInsurances = healthInsuranceRepository.findByPlanType(user.getPlanType());
            UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(user);
            userResponseDTO.setHealthInsurance(userMapper.toHealthInsuranceList(healthInsurances));
            userResponseDTO.setClient(clientMapper.toClientDTO(user.getClient()));
            return new ResponseEntity<>(MessageUtils.successMessage("Usuário encontrado com sucesso",userResponseDTO),HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException("Usuário com id " + id + " não encontrado");
        }
    }

    public ResponseEntity<ResponseDTO> findAll(){
        List<User> userList = userRepository.findFromList();
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        for (User user : userList) {
            List<HealthInsurance> healthInsurances = healthInsuranceRepository.findByPlanType(user.getPlanType());
            UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(user);
            userResponseDTO.setHealthInsurance(userMapper.toHealthInsuranceList(healthInsurances));
            userResponseDTO.setClient(clientMapper.toClientDTO(user.getClient()));
            userResponseDTOS.add(userResponseDTO);
        }
        if (!userResponseDTOS.isEmpty()){
            return new ResponseEntity<>(MessageUtils.successMessage("Os usuários foram encontrados com sucesso",userResponseDTOS),HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException("Nenhum usuário foi encontrado");
        }
    }


    public ResponseEntity<ResponseDTO> update(UserDTO userDTO, String id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()) {
            User user = opt.get();
            //String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());

            user.setLogin(userDTO.login());
            //user.setPassword(encryptedPassword);
            user.setEmail(userDTO.email());
            user.setRole(userDTO.role());
            user.setActive(userDTO.active());
            Optional<Client> clientOpt = clientRepository.findByKey(userDTO.clientKey());
            clientOpt.ifPresent(user::setClient);
            user.setPlanType(userDTO.planType());
            user.setUrl(userDTO.url());
            userRepository.save(user);
            return new ResponseEntity<>(MessageUtils.successMessage("Usuário Atualizado com sucesso",user),HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException("Nenhum usuário foi encontrado");
        }
    }

    public ResponseEntity<ResponseDTO> disable(String id) {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()) {
            User user = opt.get();
            user.setActive(!user.isActive());
            userRepository.save(user);
            return new ResponseEntity<>(MessageUtils.successMessage("Usuário Atualizado com sucesso",user),HttpStatus.OK);
        }else{
            throw new ResourceNotFoundException("Nenhum usuário foi encontrado");
        }
    }

    public ResponseEntity<ResponseDTO> delete(String id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(MessageUtils.successMessage("Usuário deletado com sucesso",null),HttpStatus.OK);
    }

    public ResponseEntity<List<PlanDTO>> findPlans() {
        List<PlanDTO> plansList = Arrays.stream(PlanType.values())
                .map(planType -> new PlanDTO(StringUtils.capitalize(planType.name().toLowerCase()),planType.name()))
                .toList();
        return new ResponseEntity<>(plansList,HttpStatus.OK);
    }

    public UserDetails findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
