package com.delivery.controller;

import com.delivery.dto.auth.LoginRequest;
import com.delivery.dto.auth.RegisterRequest;
import com.delivery.entity.enums.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterWithValidData() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setPhoneNumber("+61412345678");
        request.setPassword("password123");
        request.setVerificationCode("123456");
        request.setRole(UserRole.CUSTOMER);
        request.setName("Test User");
        request.setEmail("test@example.com");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.phoneNumber").value("+61412345678"))
                .andExpect(jsonPath("$.role").value("CUSTOMER"));
    }

    @Test
    void testRegisterWithInvalidPhoneNumber() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setPhoneNumber("123"); // 无效的手机号
        request.setPassword("password123");
        request.setVerificationCode("123456");
        request.setRole(UserRole.CUSTOMER);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginWithValidData() throws Exception {
        // 先注册一个用户
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhoneNumber("+61412345679");
        registerRequest.setPassword("password123");
        registerRequest.setVerificationCode("123456");
        registerRequest.setRole(UserRole.CUSTOMER);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        // 测试登录
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhoneNumber("+61412345679");
        loginRequest.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.phoneNumber").value("+61412345679"));
    }

    @Test
    void testLoginWithInvalidPhoneNumber() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setPhoneNumber("123"); // 无效的手机号
        request.setPassword("123456");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterMerchant() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setPhoneNumber("+61412345680");
        request.setPassword("password123");
        request.setVerificationCode("123456");
        request.setRole(UserRole.MERCHANT);
        request.setMerchantName("Test Restaurant");
        request.setMerchantAddress("123 Test St, Sydney");
        request.setBusinessLicense("LIC123456");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("MERCHANT"));
    }

    @Test
    void testRegisterRider() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setPhoneNumber("+61412345681");
        request.setPassword("password123");
        request.setVerificationCode("123456");
        request.setRole(UserRole.RIDER);
        request.setIdCardNumber("ID123456");
        request.setVehicleType("MOTORCYCLE");
        request.setVehicleNumber("ABC123");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("RIDER"));
    }
} 