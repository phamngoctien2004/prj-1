package com.web.prj.services.interfaces;

import com.web.prj.dtos.request.LoginRequest;
import com.web.prj.dtos.request.RegisterRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.LoginResponse;

public interface IAuthService {
    ApiResponse<LoginResponse> login(LoginRequest request);
    ApiResponse<String> register(RegisterRequest request);
}
