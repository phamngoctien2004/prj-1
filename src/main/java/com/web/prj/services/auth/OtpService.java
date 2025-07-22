package com.web.prj.services.auth;

import com.web.prj.services.auth.AuthService;

public interface OtpService extends AuthService {
    String sendOtp(String email);

    void saveOtpRedis(String email, String otp, String secretKey);

    void sendOtpMail(String email, String otp);
}
