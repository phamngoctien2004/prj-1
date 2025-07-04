package com.web.prj.services;

import com.web.prj.dtos.request.LoginRequest;
import com.web.prj.dtos.request.RegisterRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.LoginResponse;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.services.interfaces.IAuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    public AuthService(
            PasswordEncoder passwordEncoder,
            UserService userService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {

        return null;
    }

    @Override
    public ApiResponse<String> register(RegisterRequest request) {
        if(userService.checkEmailExisted(request.getEmail())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }


        return null;
    }
}
