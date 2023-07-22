package com.example.clothes.domain.user.dao;

import com.example.clothes.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long>{

    // 유저
    // - 로그인
    // - 회원가입
    // - 수정
    // - 회원탈퇴

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

}
