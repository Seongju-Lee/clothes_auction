package com.example.clothes.domain.item.domain;

import com.example.clothes.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User seller;

    @Column(name = "item_name")
    private String name;

    @Column(name = "item_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "item_image")
    private String imgSrc;

    @Column(name = "item_category")
    @Enumerated(EnumType.STRING)
    private ClothesCategory category;

    protected Clothes() {}

    public Clothes(User seller, String name, String description, String imgSrc, ClothesCategory category) {
        this.seller = seller;
        this.name = name;
        this.description = description;
        this.imgSrc = imgSrc;
        this.category = category;
    }


    public Clothes change(ClothesBuilder builder) {

        if (builder.getName() != null) {
            this.name = builder.getName();
        }
        if (builder.getDescription() != null) {
            this.description = builder.getDescription();
        }
        if (builder.getImgSrc() != null) {
            this.imgSrc = builder.getImgSrc();
        }
        if (builder.getCategory() != null) {
            this.category = builder.getCategory();
        }
        return this;
    }
}
