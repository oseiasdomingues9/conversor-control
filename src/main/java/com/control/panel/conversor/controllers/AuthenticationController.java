package com.control.panel.conversor.controllers;

import com.control.panel.conversor.dto.LoginDTO;
import com.control.panel.conversor.services.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        return authorizationService.checkLogin(loginDTO);
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validToken() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin-validate")
    public ResponseEntity<Void> validAdminToken() {
        return ResponseEntity.ok().build();
    }


}
