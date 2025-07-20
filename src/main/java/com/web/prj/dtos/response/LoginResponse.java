package com.web.prj.dtos.response;

import com.web.prj.dtos.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
}
