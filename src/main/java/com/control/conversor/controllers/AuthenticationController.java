package com.control.conversor.controllers;

import com.control.conversor.dto.LoginDTO;
import com.control.conversor.dto.LoginResponseDTO;
import com.control.conversor.services.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        return authorizationService.checkLogin(loginDTO);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return authorizationService.refresh(request);
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest) {
        return authorizationService.logout(httpServletRequest);
    }

//    @GetMapping("/validate")
//    public ResponseEntity<Void> validToken() {
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/admin-validate")
//    public ResponseEntity<Void> validAdminToken() {
//        return ResponseEntity.ok().build();
//    }

}
