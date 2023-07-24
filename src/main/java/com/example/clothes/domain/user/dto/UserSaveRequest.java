package com.example.clothes.domain.user.dto;

public record UserSaveRequest(
        String name,
        String email,
        String password,
        String userType
) {
}