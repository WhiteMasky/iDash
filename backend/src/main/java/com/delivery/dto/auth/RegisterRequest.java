package com.delivery.dto.auth;

import com.delivery.entity.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "手机号不能为空")
    private String phoneNumber;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String verificationCode;

    @NotNull(message = "用户角色不能为空")
    private UserRole role;

    private String name;
    
    @Email(message = "邮箱格式不正确")
    private String email;

    // 商家特有字段
    private String merchantName;
    private String merchantAddress;
    private String businessLicense;

    // 骑手特有字段
    private String idCardNumber;
    private String vehicleType;
    private String vehicleNumber;
} 