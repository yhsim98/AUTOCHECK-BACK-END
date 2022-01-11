package com.auto.check.exception;

import com.auto.check.enums.ErrorMessage;

public class NonCriticalException extends BaseException {

    public NonCriticalException(String className, ErrorMessage errorMessage) {
        super(className, errorMessage);
    }
    public NonCriticalException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
