package com.delivery.controller;

import com.delivery.dto.auth.AuthResponse;
import com.delivery.dto.auth.LoginRequest;
import com.delivery.dto.auth.RegisterRequest;
import com.delivery.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping
    @ResponseBody
    public String authInfo() {
        return "iDash Authentication API\n" +
               "Available endpoints:\n" +
               "- POST /api/auth/register - Register a new user\n" +
               "  Request body: {\"phoneNumber\": \"string\", \"password\": \"string\", \"role\": \"CUSTOMER|MERCHANT|RIDER\"}\n" +
               "- POST /api/auth/login - Login with phone number and password\n" +
               "  Request body: {\"phoneNumber\": \"string\", \"password\": \"string\"}";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
} 