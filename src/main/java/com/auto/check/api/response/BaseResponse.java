package com.auto.check.api.response;


import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class BaseResponse<T> {
    private T data;
    private Integer code;

    public static BaseResponse of(HttpStatus code){
        return of(null, code);
    }

    public static <T> BaseResponse of(@Nullable T data, HttpStatus code){
        BaseResponse responseBody = new BaseResponse();

        responseBody.data = data;
        responseBody.code = code.value();

        return responseBody;
    }
}
