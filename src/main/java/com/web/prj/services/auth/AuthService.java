package com.web.prj.services.auth;

import com.web.prj.dtos.request.LoginRequest;
import com.web.prj.dtos.response.GoogleResponse;
import com.web.prj.dtos.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    String generateLink();

    GoogleResponse getUserInfo(String code);

    LoginResponse loginGoogle(GoogleResponse userInfo);
}
