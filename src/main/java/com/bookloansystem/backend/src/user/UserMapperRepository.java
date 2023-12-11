package com.bookloansystem.backend.src.user;

import com.bookloansystem.backend.src.user.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;
import java.util.Optional;


@Mapper
public interface UserMapperRepository {
    // 회원가입
    void userInsert(User user);

    // 이메일 회원 조회
    Optional<User> findUserByEmail(@Param("email") String email);
    // 회원 아이디로 회원 조회
    Optional<User> findUserByUsername(@Param("username") String username);
}
