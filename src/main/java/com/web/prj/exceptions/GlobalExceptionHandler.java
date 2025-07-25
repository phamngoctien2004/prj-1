package com.web.prj.exceptions;

import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.ErrorFieldResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<String>> handleAppException(AppException appException) {
        ErrorCode error = appException.getErrorCode();
        ApiResponse<String> response = ApiResponse.<String>builder()
                .errors(error.getCode())
                .message(error.getMessage())
                .success(false)
                .build();

        return new ResponseEntity<ApiResponse<String>>(response, error.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handelValidationException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        List<ErrorFieldResponse> errors = fieldErrors
                .stream()
                .map((oError) -> {
                    return ErrorFieldResponse.builder()
                            .field(oError.getField())
                            .message(oError.getDefaultMessage())
                            .build();
                }).toList();

//        tạo response
        ApiResponse<?> response = ApiResponse.builder()
                .errors(errors)
                .success(false)
                .message("Lỗi dữ liệu không đúng định dạng")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .errors("500")
                .message("Internal Server Error: " + ex.getMessage())
                .success(false)
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiResponse<String>> handleSQLException(SQLException ex) {
        ApiResponse<String> response = ApiResponse.<String>builder()
                .errors("500")
                .message("Internal Server Error: " + ex.getMessage())
                .success(false)
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
