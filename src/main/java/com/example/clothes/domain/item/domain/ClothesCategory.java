package com.example.clothes.domain.item.domain;

public enum ClothesCategory {
    TOP, PANTS, OUTER, SHOES;

    public static ClothesCategory fromString(String value) {
        for (ClothesCategory clothesCategory : ClothesCategory.values()) {
            if (clothesCategory.name().equalsIgnoreCase(value)) {
                return clothesCategory;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 상품 유형입니다.");
    }
}
