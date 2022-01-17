package com.auto.check.domain.user;

import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.NonCriticalException;

public enum UserType {
    STUDENT, PROFESSOR;

    public static UserType getUserType(String userType){
        for(UserType u : UserType.values()){
            if(u.name().equals(userType)) return u;
        }

        throw new NonCriticalException(ErrorMessage.UNDEFINED_EXCEPTION);
    }
}
