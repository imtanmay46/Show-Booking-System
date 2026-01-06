package com.example.showcase.controller;

import com.example.showcase.dto.request.GoogleLoginRequest;
import com.example.showcase.dto.response.AuthResponse;
import com.example.showcase.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/google")
    public AuthResponse googleLogin(@RequestBody GoogleLoginRequest request) {
        return authService.authenticateWithGoogle(request);
    }
}
