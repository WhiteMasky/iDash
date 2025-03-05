# 跑腿服务平台 (Delivery Service Platform)

## 项目简介
这是一个基于Spring Boot + Kotlin + Swift的跑腿服务平台，支持多端应用（商家端、骑手端、加盟商管理端）。

## 技术栈
### 后端
- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- MySQL
- Redis
- JWT认证
- Google Maps API
- Stripe/PayPal支付集成

### 客户端
- Android (Kotlin)
- iOS (Swift)
- Google Maps SDK
- Firebase Cloud Messaging

## 项目结构
```
delivery-platform/
├── backend/                 # 后端服务
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── android/                 # Android客户端
│   ├── app/
│   └── README.md
├── ios/                     # iOS客户端
│   ├── DeliveryApp/
│   └── README.md
└── docs/                    # 项目文档
```

## 功能模块
1. 用户认证
   - 手机号注册/登录
   - JWT token认证
   - 角色权限管理

2. 商家端
   - 商品管理
   - 订单管理
   - 骑手分配
   - 数据统计

3. 骑手端
   - 订单接单
   - 路线导航
   - 订单状态更新
   - 收益统计

4. 加盟商管理端
   - 商家管理
   - 骑手管理
   - 订单监控
   - 数据报表

## 安全特性
- 手机号验证码登录
- JWT token认证
- 数据加密传输
- 角色基础访问控制
- 敏感数据脱敏

## 开发环境要求
- JDK 17+
- Android Studio
- Xcode
- MySQL 8.0+
- Redis 6.0+ 