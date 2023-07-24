package com.example.clothes.domain.user.dto;

public record UserUpdateRequest(
        Long userId,
        String name,
        String password,
        String userType) {
}