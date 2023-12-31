package com.example.clothes.domain.item.domain;

import com.example.clothes.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
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
}
