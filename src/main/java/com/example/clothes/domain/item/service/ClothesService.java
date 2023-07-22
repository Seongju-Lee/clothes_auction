package com.example.clothes.domain.item.service;

import com.example.clothes.domain.item.dao.ClothesRepository;
import com.example.clothes.domain.item.domain.Clothes;
import com.example.clothes.domain.item.domain.ClothesBuilder;
import com.example.clothes.domain.item.domain.ClothesCategory;
import com.example.clothes.domain.item.dto.ClothesResponse;
import com.example.clothes.domain.item.dto.ClothesRequest;
import com.example.clothes.domain.item.dto.ClothesUpdateRequest;
import com.example.clothes.domain.user.dao.UserRepository;
import com.example.clothes.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClothesService {

    private final ClothesRepository clothesRepository;
    private final UserRepository userRepository;

    public ClothesResponse save(ClothesRequest request) {
        User seller = userRepository.findById(request.sellerId()).get();
        Clothes savedClothes = clothesRepository.save(
                new Clothes(seller, request.name(), request.description(), request.imgSrc(), request.category())
        );
        return ClothesResponse.fromEntity(savedClothes);
    }

    public void delete(Long clothesId) {
        clothesRepository.deleteById(clothesId);
    }

    public ClothesResponse update(ClothesUpdateRequest request, Long clothesId) {

        Clothes clothes = clothesRepository.findById(clothesId).get();
        Clothes updatedClothes = new ClothesBuilder()
                .name(request.name())
                .description(request.description())
                .imgSrc(request.imgSrc())
                .category(request.category())
                .buildToUpdate(clothes);
        return ClothesResponse.fromEntity(clothesRepository.save(updatedClothes));
    }

    public List<ClothesResponse> findByCategory(ClothesCategory category) {
        List<Clothes> manyClothes = clothesRepository.findByCategory(category);
        return ClothesResponse.fromEntities(manyClothes);
    }

    public List<ClothesResponse> findByCategories(List<ClothesCategory> categories) {
        List<Clothes> manyClothes = clothesRepository.findByCategories(categories);
        return ClothesResponse.fromEntities(manyClothes);
    }

    public List<ClothesResponse> findBySellerId(Long sellerId) {
        List<Clothes> manyClothes = clothesRepository.findBySellerId(sellerId);
        return ClothesResponse.fromEntities(manyClothes);
    }
}
