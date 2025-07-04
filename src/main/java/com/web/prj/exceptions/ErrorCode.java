package com.web.prj.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    USER_EXISTED("USER_409", "Người dùng đã tồn tại", HttpStatus.CONFLICT);
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;


    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
