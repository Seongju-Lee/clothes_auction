package com.example.clothes.domain.user.controller;

import com.example.clothes.domain.auction.dto.AuctionRequest;
import com.example.clothes.domain.auction.service.AuctionService;
import com.example.clothes.domain.item.domain.ClothesCategory;
import com.example.clothes.domain.item.dto.ClothesRequest;
import com.example.clothes.domain.item.dto.ClothesResponse;
import com.example.clothes.domain.item.service.ClothesService;
import com.example.clothes.domain.user.dto.UserResponse;
import com.example.clothes.domain.user.dto.UserSaveRequest;
import com.example.clothes.domain.user.dto.UserUpdateRequest;
import com.example.clothes.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ClothesService clothesService;
    private final AuctionService auctionService; // test data 용도

    @PostMapping("/user")
    public ResponseEntity<UserResponse> save(
            @RequestBody UserSaveRequest request
    ) {
        UserResponse response = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/user")
    public ResponseEntity<UserResponse> update(
            @RequestBody UserUpdateRequest request
    ) {
        UserResponse response = userService.update(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> findById(
            @PathVariable Long userId
    ) {
        UserResponse response = userService.findById(userId);
        return ResponseEntity.ok(response);
    }
}