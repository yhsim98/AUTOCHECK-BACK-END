package com.auto.check.api.dto;

import com.auto.check.domain.user.User;
import com.auto.check.domain.user.UserType;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDTO {
    private Long id;
    private String account;
    private String name;
    private String schoolNumber;
    private String imgPath;
    private UserType userType;

    public UserDTO(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.name = user.getName();
        this.schoolNumber = user.getSchoolNumber();
        this.imgPath = user.getImgPath();
        this.userType = user.getUserType();
    }
}
