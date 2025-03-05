package com.delivery.service.impl;

import com.delivery.dto.auth.AuthResponse;
import com.delivery.dto.auth.LoginRequest;
import com.delivery.dto.auth.RegisterRequest;
import com.delivery.entity.User;
import com.delivery.entity.enums.UserRole;
import com.delivery.repository.UserRepository;
import com.delivery.security.JwtTokenProvider;
import com.delivery.service.AuthService;
import com.delivery.util.PhoneNumberValidator;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // 验证手机号
        if (!PhoneNumberValidator.isValidPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        // 检查手机号是否已注册
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new EntityExistsException("Phone number already registered");
        }

        // 创建用户
        User user = new User();
        user.setPhoneNumber(PhoneNumberValidator.formatPhoneNumber(request.getPhoneNumber()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // 设置角色特定字段
        if (request.getRole() == UserRole.MERCHANT) {
            user.setMerchantName(request.getMerchantName());
            user.setMerchantAddress(request.getMerchantAddress());
            user.setBusinessLicense(request.getBusinessLicense());
        } else if (request.getRole() == UserRole.RIDER) {
            user.setIdCardNumber(request.getIdCardNumber());
            user.setVehicleType(request.getVehicleType());
            user.setVehicleNumber(request.getVehicleNumber());
        }

        // 保存用户
        user = userRepository.save(user);

        // 生成token
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getPhoneNumber(), request.getPassword())
        );
        String token = jwtTokenProvider.generateToken(authentication);

        // 返回响应
        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // 验证手机号
        if (!PhoneNumberValidator.isValidPhoneNumber(request.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        // 验证用户
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword())
        );

        // 获取用户信息
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 生成token
        String token = jwtTokenProvider.generateToken(authentication);

        // 返回响应
        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
} 