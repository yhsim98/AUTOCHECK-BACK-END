package com.auto.check.domain;

import com.auto.check.enums.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Long id;
    private String account;
    private String password;
    private String name;
    private String school_number;
    @ApiModelProperty(hidden = true)
    private String img_path;
    private UserType user_type;
}