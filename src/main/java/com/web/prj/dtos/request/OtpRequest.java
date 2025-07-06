package com.web.prj.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OtpRequest {
    @NotBlank(message = "Email không được trống")
    @Email(message = "Email không đúng định dạng")
    private String email;
}
