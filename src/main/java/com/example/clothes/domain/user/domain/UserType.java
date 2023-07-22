package com.example.clothes.domain.user.domain;

public enum UserType {

    SELLER, BUYER;

    public static UserType fromString(String value) {
        for (UserType userType : UserType.values()) {
            if (userType.name().equalsIgnoreCase(value)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 userType throw from  " + UserType.class.getName());
    }
}
