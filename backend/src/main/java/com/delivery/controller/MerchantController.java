package com.delivery.controller;

import com.delivery.dto.merchant.MerchantInfoResponse;
import com.delivery.entity.User;
import com.delivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<MerchantInfoResponse> getMerchantInfo() {
        User user = userService.getCurrentUser();
        MerchantInfoResponse response = new MerchantInfoResponse();
        response.setId(user.getId());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setMerchantName(user.getMerchantName());
        response.setMerchantAddress(user.getMerchantAddress());
        response.setBusinessLicense(user.getBusinessLicense());
        return ResponseEntity.ok(response);
    }
} 