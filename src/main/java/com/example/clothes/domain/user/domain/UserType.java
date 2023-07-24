package com.example.clothes.domain.user.domain;

public enum UserType {

    SELLER, BUYER;

    public static UserType fromString(String value) {
        for (UserType userType : UserType.values()) {
            if (userType.name().equalsIgnoreCase(value)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 사용자 유형입니다.");
    }

    public static boolean isSeller(UserType type) {
        return type == SELLER;
    }
}
