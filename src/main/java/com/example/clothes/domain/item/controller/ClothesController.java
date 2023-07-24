package com.example.clothes.domain.item.controller;

import com.example.clothes.domain.item.dto.ClothesRequest;
import com.example.clothes.domain.item.dto.ClothesResponse;
import com.example.clothes.domain.item.dto.ClothesUpdateRequest;
import com.example.clothes.domain.item.service.ClothesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clothes")
@RequiredArgsConstructor
public class ClothesController {

    private final ClothesService clothesService;

    @PostMapping()
    public ResponseEntity<ClothesResponse> save(
            @RequestBody ClothesRequest request
    ) {
        ClothesResponse response = clothesService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<ClothesResponse>> findByCriteria(
            @RequestParam(required = false, name = "categories") List<String> categories,
            @RequestParam(required = false, name = "seller") Long sellerId
    ) {
        List<ClothesResponse> manyClothes = clothesService.findByCriteria(categories, sellerId);
        return ResponseEntity.ok(manyClothes);
    }

    @PatchMapping()
    public ResponseEntity<ClothesResponse> update(
            @RequestBody ClothesUpdateRequest request
    ) {
        ClothesResponse response = clothesService.update(request);
        return ResponseEntity.ok(response);
    }
}