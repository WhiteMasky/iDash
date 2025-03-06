package com.delivery.service.impl;

import com.delivery.dto.menu.MenuItemRequest;
import com.delivery.dto.menu.MenuItemResponse;
import com.delivery.entity.MenuItem;
import com.delivery.entity.User;
import com.delivery.repository.MenuItemRepository;
import com.delivery.service.MenuService;
import com.delivery.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;
    private final UserService userService;

    @Override
    @Transactional
    public MenuItemResponse createMenuItem(MenuItemRequest request) {
        User merchant = userService.getCurrentUser();
        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setImageUrl(request.getImageUrl());
        menuItem.setMerchant(merchant);
        menuItem = menuItemRepository.save(menuItem);
        return convertToResponse(menuItem);
    }

    @Override
    @Transactional
    public MenuItemResponse updateMenuItem(Long id, MenuItemRequest request) {
        MenuItem menuItem = getMenuItemOrThrow(id);
        checkMerchantOwnership(menuItem);
        menuItem.setName(request.getName());
        menuItem.setDescription(request.getDescription());
        menuItem.setPrice(request.getPrice());
        menuItem.setImageUrl(request.getImageUrl());
        menuItem = menuItemRepository.save(menuItem);
        return convertToResponse(menuItem);
    }

    @Override
    @Transactional
    public void deleteMenuItem(Long id) {
        MenuItem menuItem = getMenuItemOrThrow(id);
        checkMerchantOwnership(menuItem);
        menuItemRepository.delete(menuItem);
    }

    @Override
    public MenuItemResponse getMenuItem(Long id) {
        MenuItem menuItem = getMenuItemOrThrow(id);
        checkMerchantOwnership(menuItem);
        return convertToResponse(menuItem);
    }

    @Override
    public List<MenuItemResponse> getMerchantMenuItems() {
        User merchant = userService.getCurrentUser();
        return menuItemRepository.findByMerchant(merchant)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MenuItemResponse toggleMenuItemAvailability(Long id) {
        MenuItem menuItem = getMenuItemOrThrow(id);
        checkMerchantOwnership(menuItem);
        menuItem.setAvailable(!menuItem.isAvailable());
        menuItem = menuItemRepository.save(menuItem);
        return convertToResponse(menuItem);
    }

    private MenuItem getMenuItemOrThrow(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu item not found"));
    }

    private void checkMerchantOwnership(MenuItem menuItem) {
        User currentMerchant = userService.getCurrentUser();
        if (!menuItem.getMerchant().getId().equals(currentMerchant.getId())) {
            throw new IllegalStateException("You can only operate on your own menu items");
        }
    }

    private MenuItemResponse convertToResponse(MenuItem menuItem) {
        MenuItemResponse response = new MenuItemResponse();
        response.setId(menuItem.getId());
        response.setName(menuItem.getName());
        response.setDescription(menuItem.getDescription());
        response.setPrice(menuItem.getPrice());
        response.setImageUrl(menuItem.getImageUrl());
        response.setAvailable(menuItem.isAvailable());
        response.setCreatedAt(menuItem.getCreatedAt());
        response.setUpdatedAt(menuItem.getUpdatedAt());
        return response;
    }
} 