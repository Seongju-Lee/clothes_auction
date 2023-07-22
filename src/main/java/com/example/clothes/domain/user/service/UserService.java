package com.example.clothes.domain.user.service;

import com.example.clothes.domain.user.dao.UserRepository;
import com.example.clothes.domain.user.domain.User;
import com.example.clothes.domain.user.domain.UserType;
import com.example.clothes.domain.user.dto.UserResponse;
import com.example.clothes.domain.user.dto.UserSaveRequest;
import com.example.clothes.domain.user.dto.UserUpdateRequest;
import com.example.clothes.global.exception.custom.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserResponse save(UserSaveRequest request, String type) {
        if(userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException("중복된 이메일이 존재합니다. throw from " + UserService.class.getName());
        }
        UserType userType = UserType.fromString(type.toUpperCase());
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .type(userType)
                .build();
        User savedUser = userRepository.save(user);
        return UserResponse.fromEntity(savedUser);
    }

    public UserResponse update(UserUpdateRequest request, Long userId) {
        User user = userRepository.findById(userId).get();
        user.changeName(request.name());
        user.changePassword(request.password());
        user.changeUserType(request.userType());
        User updatedUser = userRepository.save(user);
        return UserResponse.fromEntity(updatedUser);
    }

    public UserResponse findById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원"));
        return UserResponse.fromEntity(user);
    }

}
