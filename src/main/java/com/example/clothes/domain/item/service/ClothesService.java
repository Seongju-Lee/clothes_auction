package com.example.clothes.domain.item.service;

import com.example.clothes.domain.item.dao.ClothesRepository;
import com.example.clothes.domain.item.domain.Clothes;
import com.example.clothes.domain.item.domain.ClothesCategory;
import com.example.clothes.domain.item.dto.ClothesResponse;
import com.example.clothes.domain.item.dto.ClothesRequest;
import com.example.clothes.domain.item.dto.ClothesUpdateRequest;
import com.example.clothes.domain.user.dao.UserRepository;
import com.example.clothes.domain.user.domain.User;
import com.example.clothes.domain.user.domain.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ClothesService {

    private final ClothesRepository clothesRepository;
    private final UserRepository userRepository;

    public ClothesResponse save(ClothesRequest request) {
        User seller = userRepository.findById(request.sellerId())
                .filter(user -> UserType.isSeller(user.getType()))
                .orElseThrow(() -> new NoSuchElementException("존재하지 않거나 상품등록 권한이 없습니다."));

        ClothesCategory category = ClothesCategory.fromString(request.category().toUpperCase());
        Clothes savedClothes = clothesRepository.save(
                Clothes.builder()
                        .seller(seller)
                        .name(request.name())
                        .description(request.description())
                        .imgSrc(request.imgSrc())
                        .category(category)
                        .build()
        );
        return ClothesResponse.fromEntity(savedClothes);
    }

    public void delete(Long clothesId) {
        clothesRepository.deleteById(clothesId);
    }

    public ClothesResponse update(ClothesUpdateRequest request) {

        Clothes clothes = clothesRepository.findById(request.clothesId())
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."));

        Clothes updatedClothes = Clothes.builder()
                .itemId(clothes.getItemId())
                .seller(clothes.getSeller())
                .name(request.name() != null ? request.name() : clothes.getName())
                .description(request.description() != null ? request.description() : clothes.getDescription())
                .imgSrc(request.imgSrc() != null ? request.imgSrc() : clothes.getImgSrc())
                .category(request.category() != null ? request.category() : clothes.getCategory())
                .build();
        return ClothesResponse.fromEntity(clothesRepository.save(updatedClothes));
    }

    public List<ClothesResponse> findByCriteria(
            List<String> categories,
            Long sellerId
    ) {
        if (categories == null && sellerId == null) {
            return findAll();
        } else if (categories != null && sellerId == null) {
            return findByCategories(categories);
        } else if (categories == null) {
            return findBySellerId(sellerId);
        } else {
            throw new IllegalArgumentException("올바르지 못한 요청입니다.");
        }
    }


    private List<ClothesResponse> findByCategories(List<String> categoryParam) {

        List<ClothesCategory> categories = categoryParam.stream()
                .map(ClothesCategory::fromString)
                .toList();
        List<Clothes> manyClothes = clothesRepository.findByCategories(categories);
        return ClothesResponse.fromEntities(manyClothes);
    }

    private List<ClothesResponse> findBySellerId(Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 판매자입니다."));
        List<Clothes> manyClothes = clothesRepository.findBySeller(seller);
        return ClothesResponse.fromEntities(manyClothes);
    }

    private List<ClothesResponse> findAll() {
        List<Clothes> clothes = clothesRepository.findAll();
        return ClothesResponse.fromEntities(clothes);
    }
}
