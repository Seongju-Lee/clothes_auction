package com.example.clothes.domain.user.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_type")
    private UserType type;

    public void changeName(String name) {
        this.name = name;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeUserType(UserType userType) {
        this.type = userType;
    }

    protected User() {
    }

    public User(String name, String email, String password, UserType type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public User(Long id, String name, String email, String password, UserType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }
}
