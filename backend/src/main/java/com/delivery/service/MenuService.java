package com.delivery.service;

import com.delivery.dto.menu.MenuItemRequest;
import com.delivery.dto.menu.MenuItemResponse;

import java.util.List;

public interface MenuService {
    MenuItemResponse createMenuItem(MenuItemRequest request);
    MenuItemResponse updateMenuItem(Long id, MenuItemRequest request);
    void deleteMenuItem(Long id);
    MenuItemResponse getMenuItem(Long id);
    List<MenuItemResponse> getMerchantMenuItems();
    MenuItemResponse toggleMenuItemAvailability(Long id);
} 