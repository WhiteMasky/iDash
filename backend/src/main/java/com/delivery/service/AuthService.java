package com.delivery.service;

import com.delivery.dto.auth.AuthResponse;
import com.delivery.dto.auth.LoginRequest;
import com.delivery.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
} 