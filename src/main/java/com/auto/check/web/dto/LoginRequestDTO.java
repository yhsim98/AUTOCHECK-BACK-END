package com.auto.check.web.dto;

import com.auto.check.domain.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginRequestDTO {
    private String account;
    private String password;

    public User toEntity(){
        return User.builder()
                .account(account)
                .password(password)
                .build();
    }
}
