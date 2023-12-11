package com.bookloansystem.backend.src.user.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
@Getter
@NoArgsConstructor
@Data
public class User {

    private String userId;
    private String username;
    private String password;
    private String name;
    private String email;
    private Date createdAt;

    @Builder
    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

}
