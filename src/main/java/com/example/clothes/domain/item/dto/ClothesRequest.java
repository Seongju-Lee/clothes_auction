package com.example.clothes.domain.item.dto;

import com.example.clothes.domain.item.domain.ClothesCategory;

public record ClothesRequest(Long sellerId, String name, String description, String imgSrc, ClothesCategory category) {
}
