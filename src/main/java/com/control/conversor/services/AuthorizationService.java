package com.control.conversor.services;

import com.control.conversor.dto.StatusResponseDTO;
import com.control.conversor.dto.LoginDTO;
import com.control.conversor.dto.LoginResponseDTO;
import com.control.conversor.entities.User;
import com.control.conversor.repositories.UserRepository;
import com.control.conversor.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserRepository userRepository;

    @Autowired
    public AuthorizationService(@Lazy AuthenticationManager authenticationManager, TokenUtils tokenUtils, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.userRepository = userRepository;
    }


    //Verificar se usuario existe
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    public ResponseEntity<?> checkLogin(LoginDTO loginDTO){
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var user = (User) auth.getPrincipal();
            var token = tokenUtils.generateToken((User) auth.getPrincipal());
            return new ResponseEntity<>(new LoginResponseDTO(token,user.getId()), HttpStatus.OK);
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(new StatusResponseDTO(e.getMessage(),null,true),HttpStatus.FORBIDDEN);
        }catch (Exception e){
            return new ResponseEntity<>(new StatusResponseDTO(e.getMessage(),null,true),HttpStatus.BAD_REQUEST);
        }
    }

}
