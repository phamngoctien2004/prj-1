package com.web.prj.services.auth;

public interface VerifyService extends AuthService {
    String sendOtp(String email);

    void saveOtpRedis(String email, String otp, String secretKey);

    void sendOtpMail(String email, String otp);
}
