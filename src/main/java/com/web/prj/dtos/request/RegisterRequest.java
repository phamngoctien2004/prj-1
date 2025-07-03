package com.web.prj.dtos.request;

import com.web.prj.enums.Gender;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String retypePassword;
    private String name;
    private Gender gender;
}
