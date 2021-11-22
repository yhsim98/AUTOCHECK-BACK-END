package com.auto.check.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private Long number;
    private Short is_attend;
    private String img_path;
}