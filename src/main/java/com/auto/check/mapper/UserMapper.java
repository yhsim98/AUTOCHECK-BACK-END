package com.auto.check.mapper;

import com.auto.check.domain.User;

public interface UserMapper {
    User getUserById(Long id);
    User getPasswordByAccount(String account);
    void insertUser(User user);
    User getUserByAccount(String account);
    User getUserByNumber(String number);
}
