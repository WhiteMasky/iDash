package com.delivery.entity;

import com.delivery.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String name;
    private String email;
    private String avatar;

    @Column(nullable = false)
    private boolean enabled = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 商家特有字段
    @Column(name = "merchant_name")
    private String merchantName;
    
    @Column(name = "merchant_address")
    private String merchantAddress;
    
    @Column(name = "business_license")
    private String businessLicense;

    // 骑手特有字段
    @Column(name = "id_card_number")
    private String idCardNumber;
    
    @Column(name = "vehicle_type")
    private String vehicleType;
    
    @Column(name = "vehicle_number")
    private String vehicleNumber;
    
    @Column(name = "current_location")
    private String currentLocation;
    
    @Column(name = "is_available")
    private boolean isAvailable = true;
} 