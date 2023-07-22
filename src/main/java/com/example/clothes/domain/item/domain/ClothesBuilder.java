package com.example.clothes.domain.item.domain;

import lombok.Getter;

@Getter
public class ClothesBuilder {

    private String name;
    private String description;
    private String imgSrc;
    private ClothesCategory category;

    public ClothesBuilder() {
    }

    public ClothesBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ClothesBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ClothesBuilder imgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
        return this;
    }

    public ClothesBuilder category(ClothesCategory category) {
        this.category = category;
        return this;
    }

    // 업데이트를 위해 빌더 객체를 생성하는 메서드
    public Clothes buildToUpdate(Clothes clothes) {
        return clothes.change(this);
    }
}
