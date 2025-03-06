package com.delivery.controller;

import com.delivery.dto.menu.MenuItemRequest;
import com.delivery.dto.menu.MenuItemResponse;
import com.delivery.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merchant/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuItemResponse> createMenuItem(@Valid @RequestBody MenuItemRequest request) {
        return ResponseEntity.ok(menuService.createMenuItem(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemRequest request) {
        return ResponseEntity.ok(menuService.updateMenuItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> getMenuItem(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuItem(id));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponse>> getMerchantMenuItems() {
        return ResponseEntity.ok(menuService.getMerchantMenuItems());
    }

    @PatchMapping("/{id}/toggle-availability")
    public ResponseEntity<MenuItemResponse> toggleMenuItemAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.toggleMenuItemAvailability(id));
    }
} 