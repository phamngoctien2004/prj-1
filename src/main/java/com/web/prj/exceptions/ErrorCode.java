package com.web.prj.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    NOT_NULL("NOT_NULL_400", "Giá trị không xác định", HttpStatus.BAD_REQUEST),
    USER_EXISTED("USER_409", "Người dùng đã tồn tại", HttpStatus.CONFLICT),
    RECORD_EXISTED("REC_409", "Bản ghi đã tồn tại", HttpStatus.CONFLICT),
    RECORD_NOTFOUND("REC_404", "Bản ghi không tồn tại", HttpStatus.NOT_FOUND),
    RECORD_UNDELETED("REC_400", "Bản khi không thể xóa", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("USER_404", "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND("ROLE_404", "Không tìm thấy role", HttpStatus.NOT_FOUND),
    OTP_INVALID("OTP_401", "Mã otp không chính xác", HttpStatus.UNAUTHORIZED),
    OTP_CANNOT_RESEND("OTP_429", "Chưa được gửi lại otp", HttpStatus.TOO_MANY_REQUESTS),
    SEND_EMAIL_FAILED("EMAIL_400", "Gửi email thất bại", HttpStatus.BAD_REQUEST);

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
