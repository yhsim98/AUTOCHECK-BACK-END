package com.auto.check.domain.user;

import com.auto.check.domain.DefaultEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name="user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
public class User extends DefaultEntity {

    private String account;

    private String password;

    private String name;

    @Column(name="school_number")
    private String schoolNumber;

    @ApiModelProperty(hidden = true)
    @Column(name="img_path")
    private String imgPath;

    @Enumerated(EnumType.STRING)
    @Column(name="user_type")
    private UserType userType;

    @Builder
    public User(String account, String password, String name, String schoolNumber, String imgPath, UserType userType) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.schoolNumber = schoolNumber;
        this.imgPath = imgPath;
        this.userType = userType;
    }

    public void changePasswordBcrypt(String password){
        this.password = password;
    }
}