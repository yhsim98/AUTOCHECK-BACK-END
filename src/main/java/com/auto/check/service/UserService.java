package com.auto.check.service;

import com.auto.check.domain.User;

import java.util.Map;

public interface UserService {
    User getLoginUserInfo();
    Map userLogin(User user);
}