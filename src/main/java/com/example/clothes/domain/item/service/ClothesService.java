package com.example.clothes.domain.item.service;

import com.example.clothes.domain.item.dao.ClothesRepository;
import com.example.clothes.domain.item.domain.Clothes;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClothesService {

    private final ClothesRepository clothesRepository;
    private final UserRepository userRepository;
    // 저장
    public ClothesResponse save(ClothesRequest request) {
        User user = userRepository.findById(request.sellerId()).get();
        Clothes clothes = Clothes.builder()
                .seller(user)
                .name(request.name())
                .description(request.description())
                .imgSrc(request.imgSrc())
                .category(request.category())
                .build();
        Clothes savedClothes = clothesRepository.save(clothes);
        return ClothesResponse.fromEntity(savedClothes);
    }

    // 삭제
    public void delete(Long clothesId) {
        clothesRepository.deleteById(clothesId);
    }

    // 변경(name, description, image)
    public ClothesResponse update(ClothesUpdateRequest request, Long clothesId) {

        Clothes clothes = clothesRepository.findById(clothesId).get();
        Clothes updatedClothes = clothesRepository.save(
                Clothes.builder()
                        .name(request.name() == null ? clothes.getName() : request.name())
                        .description(request.description() == null ? clothes.getDescription() : request.description())
                        .imgSrc(request.imgSrc() == null ? clothes.getImgSrc() : request.imgSrc())
                        .category(request.category() == null ? clothes.getCategory() : request.category())
                        .build()
        );
        return ClothesResponse.fromEntity(updatedClothes);
    }

    // 카테고리로 조회
    public List<ClothesResponse> findByCategory(ClothesCategory category) {
        List<Clothes> manyClothes = clothesRepository.findByCategory(category);
        return ClothesResponse.fromEntities(manyClothes);
    }

    // 여러 카테고리 조회
    public List<ClothesResponse> findByCategories(List<ClothesCategory> categories) {
        List<Clothes> manyClothes = clothesRepository.findByCategories(categories);
        return ClothesResponse.fromEntities(manyClothes);
    }

    // 유저가 등록한 상품 조회
    public List<ClothesResponse> findBySellerId(Long sellerId) {
        List<Clothes> manyClothes = clothesRepository.findBySellerId(sellerId);
        return ClothesResponse.fromEntities(manyClothes);
    }
}
