package com.example.clothes.domain.item.dao;

import com.example.clothes.domain.item.domain.Clothes;
import com.example.clothes.domain.item.domain.ClothesCategory;
import com.example.clothes.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    List<Clothes> findBySeller(User seller);

    @Query("SELECT c FROM Clothes c WHERE UPPER(c.category) in :categories")
    List<Clothes> findByCategories(@Param("categories") List<ClothesCategory> categories);
}
