package com.delivery.dto.merchant;

import lombok.Data;

@Data
public class MerchantInfoResponse {
    private Long id;
    private String phoneNumber;
    private String name;
    private String email;
    private String merchantName;
    private String merchantAddress;
    private String businessLicense;
} 