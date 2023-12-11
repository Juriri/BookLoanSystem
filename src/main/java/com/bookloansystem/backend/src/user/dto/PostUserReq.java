package com.bookloansystem.backend.src.user.dto;

import com.bookloansystem.backend.src.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserReq {
    private String username;
    private String password;
    private String name;
    private String email;

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .password(this.password)
                .name(this.name)
                .email(this.email)
                .build();
    }

}