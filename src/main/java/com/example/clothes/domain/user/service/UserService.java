package com.example.clothes.domain.user.service;

import com.example.clothes.domain.user.dao.UserRepository;
import com.example.clothes.domain.user.domain.User;
import com.example.clothes.domain.user.domain.UserType;
import com.example.clothes.domain.user.dto.UserResponse;
import com.example.clothes.domain.user.dto.UserSaveRequest;
import com.example.clothes.domain.user.dto.UserUpdateRequest;
import com.example.clothes.global.exception.custom.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserResponse save(UserSaveRequest request) {
        checkDuplicateEmail(request.email());

        UserType userType = UserType.fromString(request.userType().toUpperCase());
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .type(userType)
                .build();
        User savedUser = userRepository.save(user);
        return UserResponse.fromEntity(savedUser);
    }

    public UserResponse update(UserUpdateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
        UserType userType = UserType.fromString(request.userType().toUpperCase());
        User updateUser = User.builder()
                .id(request.userId())
                .name(request.name() != null ? request.name() : user.getName())
                .email(user.getEmail())
                .password(request.password() != null ? request.password() : user.getPassword())
                .type(userType)
                .build();
        return UserResponse.fromEntity(userRepository.save(updateUser));
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
        return UserResponse.fromEntity(user);
    }


    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("중복된 이메일이 존재합니다.");
        }
    }
}