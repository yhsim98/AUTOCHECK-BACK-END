package com.auto.check.enums;

public enum ErrorMessage {
    UNDEFINED_EXCEPTION(0, "원인을 알 수 없는 예외"),

    // user
    ACCOUNT_NOT_EXIST(1, "존재하지 않는 아이디입니다."),
    WRONG_PASSWORD_EXCEPTION(2, "틀린 비밀번호입니다."),
    JWT_NOT_START_BEARER(3, "jwt 가 Bearer 로 시작하지 않습니다"),
    JWT_NOT_EXIST(4, "jwt 이 존재하지 않습니다"),
    TOKEN_EXPIRED_EXCEPTION(5, "token 기간 만료"),
    TOKEN_INVALID_EXCEPTION(6, "유효하지 않은 token"),
    ACCOUNT_ALREADY_EXIST(7, "이미 존재하는 계정입니다"),
    SCHOOL_NUMBER_ALREADY_EXIST(8, "이미 존재하는 학번 또는 교번입니다"),
    STUDENT_NOT_REGISTRATION(9, "해당 강의를 수강하지 않는 학생입니다"),
    STUDENT_ALREADY_REGISTRATION(10, "이미 수강하고 있습니다"),
    USER_NOT_EXIST(11, "존재하지 않는 유저입니다"),

    // lecture
    USER_NOT_PROFESSOR(100, "user id 가 교수가 아닙니다"),
    LECTURE_NOT_EXIST(101, "존재하지 않는 강의입니다"),

    // registration
    ALREADY_REGISTRATION(500, "이미 수강되어 있습니다"),
    
    //file
    FILE_UPLOAD_FAIL(1000, "파일 s3 업로드 실패"),
    
    //image
    IMAGE_NOT_EXIST(1500, "이미지가 존재하지 않습니다")
    ;

    Integer code;
    String errorMessage;
    ErrorMessage(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }


    public Integer getCode() {
        return code;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
