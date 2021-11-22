package com.auto.check.mapper;

import com.auto.check.domain.User;

public interface UserMapper {
    User getUserById(Long id);
    User getPasswordByAccount(String account);
}
