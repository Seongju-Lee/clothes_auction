package com.example.clothes.domain.user.dto;

import com.example.clothes.domain.user.domain.UserType;

public record UserUpdateRequest(
        String name,
        String password,
        UserType userType) {
}