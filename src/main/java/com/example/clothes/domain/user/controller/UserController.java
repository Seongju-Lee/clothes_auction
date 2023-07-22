package com.example.clothes.domain.user.controller;

import com.example.clothes.domain.auction.dto.AuctionRequest;
import com.example.clothes.domain.auction.service.AuctionService;
import com.example.clothes.domain.bid.dto.BidInfoResponse;
import com.example.clothes.domain.bid.service.BidService;
import com.example.clothes.domain.item.domain.ClothesCategory;
import com.example.clothes.domain.item.dto.ClothesRequest;
import com.example.clothes.domain.item.dto.ClothesResponse;
import com.example.clothes.domain.item.service.ClothesService;
import com.example.clothes.domain.user.dto.UserResponse;
import com.example.clothes.domain.user.dto.UserSaveRequest;
import com.example.clothes.domain.user.dto.UserUpdateRequest;
import com.example.clothes.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ClothesService clothesService;
    private final AuctionService auctionService; // test data 용도

    @PostMapping("/users/signup/{userType}")
    public ResponseEntity<String> save(
            @RequestBody UserSaveRequest userSaveRequest,
            @PathVariable String userType
    ) {
        UserResponse response = userService.save(userSaveRequest, userType);
        return ResponseEntity.ok(response.getName() + " 님의 회원가입이 완료되었습니다.");
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<String> update(
            @RequestBody UserUpdateRequest userUpdateRequest,
            @PathVariable Long userId
    ) {
        UserResponse response = userService.update(userUpdateRequest, userId);
        return ResponseEntity.ok(response.getName() + " 님의 회원정보 수정이 완료되었습니다.");
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<Map<String, UserResponse>> findById(
            @PathVariable Long userId
    ) {
        UserResponse user = userService.findById(userId);
        HashMap<String, UserResponse> returnMap = new HashMap<>();
        returnMap.put("user", user);
        return ResponseEntity.ok(returnMap);
    }

    @GetMapping("/users/{sellerId}/clothes")
    public ResponseEntity<List<ClothesResponse>> findClothesBySellerId(
            @PathVariable Long sellerId
    ) {
        List<ClothesResponse> response = clothesService.findBySellerId(sellerId);
        return ResponseEntity.ok(response);
    }

    @PostConstruct
    public void init() {
        UserSaveRequest saveRequest = new UserSaveRequest("lee", "lee@gmail.com", "1234");
        UserSaveRequest saveRequest2 = new UserSaveRequest("lee222", "lee222@gmail.com", "1234");
        userService.save(saveRequest, "SELLER");
        userService.save(saveRequest2, "BUYER");

        ClothesRequest request1 = new ClothesRequest(1L, "신발이다.", "신발 잘 모름;", "https://img-src", ClothesCategory.SHOES);
        ClothesRequest request2 = new ClothesRequest(1L, "신발이다22.", "신발 잘 모름22;", "https://img-src22", ClothesCategory.SHOES);
        ClothesRequest request3 = new ClothesRequest(1L, "상의다.", "상의 잘 모름22;", "https://img-src22", ClothesCategory.TOP);

        clothesService.save(request1);
        clothesService.save(request2);
        clothesService.save(request3);

        AuctionRequest request = new AuctionRequest(1L, 10_000L, LocalDateTime.now());
        auctionService.save(request);
    }
}