package com.example.clothes.domain.item.controller;

import com.example.clothes.domain.item.domain.ClothesCategory;
import com.example.clothes.domain.item.dto.ClothesRequest;
import com.example.clothes.domain.item.dto.ClothesResponse;
import com.example.clothes.domain.item.dto.ClothesUpdateRequest;
import com.example.clothes.domain.item.service.ClothesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClothesController {

    private final ClothesService clothesService;

    @PostMapping("/clothes")
    public ResponseEntity<String> save(
            @RequestBody ClothesRequest request
    ) {
        ClothesResponse response = clothesService.save(request);
        return ResponseEntity.ok("상품이 등록 되었습니다. 상품명: [" + response.name() + "]");
    }

    @GetMapping("/clothes")
    public ResponseEntity<List<ClothesResponse>> findByCategory(
            @RequestParam("categories") List<ClothesCategory> categories
    ) {
        List<ClothesResponse> manyClothes = clothesService.findByCategories(categories);
        return ResponseEntity.ok(manyClothes);
    }

    @DeleteMapping("/clothes/{clothesId}")
    public ResponseEntity<String> delete(
            @PathVariable Long clothesId
    ) {
        clothesService.delete(clothesId);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

    @PatchMapping("/clothes/{clothesId}")
    public ResponseEntity<Map<String, ClothesResponse>> update(
            @RequestBody ClothesUpdateRequest request,
            @PathVariable Long clothesId
    ) {
        ClothesResponse response = clothesService.update(request, clothesId);
        HashMap<String, ClothesResponse> returnMap = new HashMap<>();
        returnMap.put("clothes", response);
        return ResponseEntity.ok(returnMap);
    }
}