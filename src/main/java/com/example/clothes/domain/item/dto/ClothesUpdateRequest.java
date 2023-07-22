package com.example.clothes.domain.item.dto;

import com.example.clothes.domain.item.domain.ClothesCategory;

public record ClothesUpdateRequest(
        String name,
        String description,
        String imgSrc,
        ClothesCategory category
) {
}
