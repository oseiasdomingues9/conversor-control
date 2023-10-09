package com.control.conversor.services;

import com.control.conversor.dto.LoginDTO;
import com.control.conversor.dto.LoginResponseDTO;
import com.control.conversor.entities.RefreshToken;
import com.control.conversor.entities.User;
import com.control.conversor.exception.ApplicationException;
import com.control.conversor.exception.CredentialException;
import com.control.conversor.utils.HeaderUtils;
import com.control.conversor.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthorizationService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthorizationService(@Lazy AuthenticationManager authenticationManager, UserService userService, RefreshTokenService refreshTokenService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtils = jwtUtils;
    }
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userService.findByLogin(login);
    }

    public ResponseEntity<LoginResponseDTO> checkLogin(LoginDTO loginDTO){
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
            var auth = authenticationManager.authenticate(usernamePassword);

            SecurityContextHolder.getContext().setAuthentication(auth);

            var user = (User) auth.getPrincipal();
            var jwtCookie = jwtUtils.generateJwtCookie(user);

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
            var jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.getToken());

            HttpHeaders httpHeaders = HeaderUtils.getHeaders(jwtCookie,jwtRefreshCookie);

            return new ResponseEntity<>(new LoginResponseDTO(user.getId()), httpHeaders,HttpStatus.OK);
        }catch (BadCredentialsException e){
            throw new CredentialException(e.getMessage());
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }
    
    public ResponseEntity<Void> refresh(HttpServletRequest request) {
        return refreshTokenService.refreshToken(jwtUtils.getJwtRefreshFromCookies(request));
    }

    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String userId = request.getHeader("user");
        if(userId != null){
            refreshTokenService.deleteByUserId(userId);
        }else{
            throw new RuntimeException("ERRO AO DESLOGAR");
        }

        var jwtCookie = jwtUtils.getCleanJwtCookie();
        var jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();
        var httpHeaders = HeaderUtils.getHeaders(jwtCookie,jwtRefreshCookie);
        return new ResponseEntity<>(httpHeaders,HttpStatus.OK);
    }

}
