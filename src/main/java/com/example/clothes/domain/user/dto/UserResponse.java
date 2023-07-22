package com.example.clothes.domain.user.dto;

import com.example.clothes.domain.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class UserResponse {

    private String name;
    private String email;
    private String password;

    public static UserResponse fromEntity(User user) {
        UserResponse response = new UserResponse();
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPassword(user.getPassword());
        return response;
    }
}