package com.web.prj.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String code;
    private T data;
    private T errors;
    private boolean success;
    private String message;

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }
}
