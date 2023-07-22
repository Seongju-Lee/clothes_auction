package com.example.clothes.domain.item.dto;

import com.example.clothes.domain.item.domain.Clothes;

import java.util.List;
import java.util.stream.Collectors;

public record ClothesResponse(
        String name,
        String description,
        String imgSrc
) {

    public static ClothesResponse fromEntity(Clothes clothes) {
        return new ClothesResponse(clothes.getName(), clothes.getDescription(), clothes.getImgSrc());
    }

    public static List<ClothesResponse> fromEntities(List<Clothes> manyClothes) {
        return manyClothes.stream()
                .map(ClothesResponse::fromEntity)
                .collect(Collectors.toList());
    }
}