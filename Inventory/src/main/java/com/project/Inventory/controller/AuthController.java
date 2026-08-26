package com.project.Inventory.controller;

import com.project.Inventory.dto.LoginRequestDTO;
import com.project.Inventory.dto.LoginResponseDTO;
import com.project.Inventory.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(
            @RequestBody LoginRequestDTO request) {

        String token = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        return new LoginResponseDTO(token);
    }
}