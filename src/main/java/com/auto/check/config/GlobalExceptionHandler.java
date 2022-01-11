package com.auto.check.config;

import com.auto.check.enums.ErrorMessage;
import com.auto.check.exception.BaseException;
import com.auto.check.exception.NonCriticalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseException> defaultException(Throwable e, HandlerMethod handlerMethod) throws IOException {
        BaseException baseException = null;

        if (e instanceof NonCriticalException){
            ((NonCriticalException) e).setErrorTrace(e.getStackTrace()[0].toString());
            baseException = (NonCriticalException) e;
        }

        if(baseException == null){
            baseException = new BaseException(e.getClass().getSimpleName(), ErrorMessage.UNDEFINED_EXCEPTION);
            baseException.setErrorMessage(e.getMessage());
            baseException.setErrorTrace(e.getStackTrace()[0].toString());
        }

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }
}
