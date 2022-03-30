package com.auto.check.api.dto;

import com.auto.check.domain.user.User;
import com.auto.check.domain.user.UserType;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SignInRequestDTO {
    private String account;
    private String password;
    private String name;
    private String schoolNumber;
    private UserType userType;

    public User toEntity(){
        return User.builder()
                .account(account)
                .password(password)
                .name(name)
                .schoolNumber(schoolNumber)
                .userType(userType)
                .build();
    }
}
