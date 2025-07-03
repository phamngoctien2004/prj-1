package com.web.prj.dtos.response;

public class ApiResponse<T> {
    private T data;
    private T errors;
    private boolean success;
    private int status;
    private String message;
}
