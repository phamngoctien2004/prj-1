package com.web.prj.services.cores;

import com.web.prj.dtos.request.LoginRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.GoogleResponse;
import com.web.prj.dtos.response.LoginResponse;
import com.web.prj.services.helpers.OtpService;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    String generateLink();

    GoogleResponse getUserInfo(String code);

    LoginResponse loginGoogle(GoogleResponse userInfo);
}
