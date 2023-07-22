package com.example.clothes.domain.user.controller;

import com.example.clothes.domain.user.domain.User;
import com.example.clothes.domain.user.domain.UserType;
import com.example.clothes.domain.user.dto.UserResponse;
import com.example.clothes.domain.user.dto.UserSaveRequest;
import com.example.clothes.domain.user.dto.UserUpdateRequest;
import com.example.clothes.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/v1/users/signup/{userType}")
    public ResponseEntity<String> save(@RequestBody UserSaveRequest userSaveRequest, @PathVariable String userType) {
        UserResponse response = userService.save(userSaveRequest, userType);
        return ResponseEntity.ok(response.getName() + " 님의 회원가입이 완료되었습니다.");
    }

    @PutMapping("/api/v1/users/{userId}")
    public ResponseEntity<String> update(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable Long userId) {
        UserResponse response = userService.update(userUpdateRequest, userId);
        return ResponseEntity.ok(response.getName() + " 님의 회원정보 수정이 완료되었습니다.");
    }

    @GetMapping("/api/v1/users/{userId}")
    public ResponseEntity<Map<String, UserResponse>> findById(@PathVariable Long userId) {
        UserResponse user = userService.findById(userId);
        HashMap<String, UserResponse> returnMap = new HashMap<>();
        returnMap.put("user", user);
        return ResponseEntity.ok(returnMap);
    }
}