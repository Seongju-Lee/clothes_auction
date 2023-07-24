package com.example.clothes.domain.item.dto;

public record ClothesRequest(
        Long sellerId,
        String name,
        String description,
        String imgSrc,
        String category) {
}
