package com.web.prj.dtos.response;

import com.web.prj.dtos.UserDTO;
import lombok.Data;

import java.util.List;
@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;

    private UserDTO user;
    private String role;
}
