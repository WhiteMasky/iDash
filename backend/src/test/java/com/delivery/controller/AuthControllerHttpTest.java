package com.delivery.controller;

import com.delivery.dto.auth.LoginRequest;
import com.delivery.dto.auth.RegisterRequest;
import com.delivery.entity.enums.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuthControllerHttpTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterWithGlobalPhoneNumbers() throws Exception {
        // 测试不同国家的手机号注册
        String[] phoneNumbers = {
            "+61412345678", // 澳大利亚
            "+8613812345678", // 中国
            "+14155552671", // 美国
            "+447911123456", // 英国
            "+6591234567", // 新加坡
            "+819012345678", // 日本
            "+821012345678"  // 韩国
        };

        for (String phoneNumber : phoneNumbers) {
            RegisterRequest request = new RegisterRequest();
            request.setPhoneNumber(phoneNumber);
            request.setPassword("password123");
            request.setVerificationCode("123456");
            request.setRole(UserRole.CUSTOMER);
            request.setName("Test User");
            request.setEmail("test@example.com");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(
                objectMapper.writeValueAsString(request), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/auth/register", entity, String.class);

            assertEquals(200, response.getStatusCode().value());
            String body = response.getBody();
            assertNotNull(body, "响应体不应为空");
            assertTrue(body.contains(phoneNumber));
        }
    }

    @Test
    void testLoginWithGlobalPhoneNumbers() throws Exception {
        // 测试不同国家的手机号登录
        String[] phoneNumbers = {
            "+61412345678", // 澳大利亚
            "+8613812345678", // 中国
            "+14155552671", // 美国
            "+447911123456", // 英国
            "+6591234567", // 新加坡
            "+819012345678", // 日本
            "+821012345678"  // 韩国
        };

        for (String phoneNumber : phoneNumbers) {
            // 先注册用户
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setPhoneNumber(phoneNumber);
            registerRequest.setPassword("password123");
            registerRequest.setVerificationCode("123456");
            registerRequest.setRole(UserRole.CUSTOMER);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> registerEntity = new HttpEntity<>(
                objectMapper.writeValueAsString(registerRequest), headers);

            restTemplate.postForEntity("/api/auth/register", registerEntity, String.class);

            // 测试登录
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setPhoneNumber(phoneNumber);
            loginRequest.setPassword("password123");

            HttpEntity<String> loginEntity = new HttpEntity<>(
                objectMapper.writeValueAsString(loginRequest), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/auth/login", loginEntity, String.class);

            assertEquals(200, response.getStatusCode().value());
            String body = response.getBody();
            assertNotNull(body, "响应体不应为空");
            assertTrue(body.contains(phoneNumber));
            assertTrue(body.contains("token"));
        }
    }

    @Test
    void testInvalidPhoneNumbers() throws Exception {
        // 测试无效的手机号
        String[] invalidPhoneNumbers = {
            "123", // 太短
            "+12345678901234567890", // 太长
            "+6141234567a", // 包含字母
            "", // 空字符串
            null // null
        };

        for (String phoneNumber : invalidPhoneNumbers) {
            RegisterRequest request = new RegisterRequest();
            request.setPhoneNumber(phoneNumber);
            request.setPassword("password123");
            request.setVerificationCode("123456");
            request.setRole(UserRole.CUSTOMER);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(
                objectMapper.writeValueAsString(request), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/auth/register", entity, String.class);

            assertEquals(400, response.getStatusCode().value());
        }
    }
} 