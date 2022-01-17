package com.auto.check.config;


import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ResponseData {
    private Object data;
    private Integer code;

    public static ResponseData of(HttpStatus code){
        return of(null, code);
    }

    public static ResponseData of(@Nullable Object data, HttpStatus code){
        ResponseData responseBody = new ResponseData();

        responseBody.data = data;
        responseBody.code = code.value();

        return responseBody;
    }
}
