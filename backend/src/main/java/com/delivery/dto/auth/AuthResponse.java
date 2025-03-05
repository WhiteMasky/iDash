package com.delivery.dto.auth;

import com.delivery.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private String phoneNumber;
    private UserRole role;
    private String name;
    private String email;
} 