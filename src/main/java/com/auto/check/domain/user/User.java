package com.auto.check.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name="user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String account;

    private String password;

    private String name;

    private String school_number;

    @ApiModelProperty(hidden = true)
    private String img_path;

    @Enumerated(EnumType.STRING)
    private UserType user_type;

    @Builder
    public User(String account, String password, String name, String school_number, String img_path, UserType user_type) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.school_number = school_number;
        this.img_path = img_path;
        this.user_type = user_type;
    }
}