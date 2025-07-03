package com.web.prj.dtos.response;

import com.web.prj.dtos.UserDTO;

import java.util.List;

public class LoginResponse {
    private String accessToken;
    private String refreshToken;

    private UserDTO user;
    private String role;
    private List<String> permissions;
}
