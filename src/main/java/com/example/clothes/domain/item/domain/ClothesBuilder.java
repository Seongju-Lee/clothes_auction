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

    public Clothes build(Clothes clothes) {
        return clothes.update(this);
    }
}
