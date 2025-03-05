package com.delivery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/")
    public String apiInfo() {
        return "iDash Delivery Platform API\n" +
               "Available endpoints:\n" +
               "- /api/auth - Authentication endpoints\n" +
               "  - POST /api/auth/register - Register a new user\n" +
               "  - POST /api/auth/login - Login with phone number and password\n" +
               "- /api/admin - Admin management endpoints\n" +
               "- /api/merchant - Merchant management endpoints\n" +
               "- /api/rider - Rider management endpoints\n" +
               "- /api/customer - Customer management endpoints";
    }
} 