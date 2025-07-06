package com.web.prj.services;

import com.web.prj.Helpers.OtpHelper;
import com.web.prj.dtos.request.LoginRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.LoginResponse;
import com.web.prj.entities.Role;
import com.web.prj.entities.User;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.services.interfaces.IAuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectURI;

    @Value("${spring.security.oauth2.client.registration.google.scope}")
    private String scope;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenURI;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String userInfoURI;

    private String urlBase = "https://accounts.google.com/o/oauth2/v2/auth";


    private final UserService userService;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        String key = "otp:" + request.getEmail();
        Object value = redisTemplate.opsForValue().get(key);
//        kieerm tra otp
        if(!request.getOtp().equals(value)){
            throw new AppException(ErrorCode.OTP_INVALID);
        }
        Optional<User> oUser = userService.findByEmail(request.getEmail());
        User user = oUser.orElseGet(() -> userService.createUser(request.getEmail()));

        return ApiResponse.<LoginResponse>builder()
                .code("200")
                .success(true)
                .data(toLoginResponse(user))
                .build();
    }

    @Override
    public ApiResponse<String> sendOtp(String email) {
        String otp = OtpHelper.generateTOTP(secretKey);
        saveOtpRedis(email, otp);

        sendOtpMail(email, otp);



        return ApiResponse.<String>builder()
                .data(otp)
                .message("otp đã được gửi vào email - " + email)
                .success(true)
                .build();
    }

    @Override
    public ApiResponse<String> generateLink() {
        String url = UriComponentsBuilder
                .fromPath(urlBase)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectURI)
                .queryParam("response_type", "code")
                .queryParam("scope", scope)
                .queryParam("state", UUID.randomUUID().toString())
                .queryParam("access_type", "online")
                .queryParam("prompt", "consent")
                .build()
                .toUriString();

        return new ApiResponse<>("200",url,null, true, "Tạo link thành công");
    }

    public void saveOtpRedis(String email, String otp){
        String key = "otp:" + email;
        redisTemplate.opsForValue().set(key, otp, Duration.ofMinutes(5));
    }
    public void sendOtpMail(String email, String otp){
        String content = OtpHelper.emailContent.replace("123456", otp);
        emailService.sendEmail(email, content, "Mã xác thực otp");
    }

    public LoginResponse toLoginResponse(User user){
        LoginResponse response = new LoginResponse();
        response.setUser(userService.toDto(user));
        response.setRole(user.getRole().getCode());
        response.setAccessToken(jwtService.generate(user.getEmail(), List.of("USER"), 5));
        response.setRefreshToken(jwtService.generate(user.getEmail(), List.of("USER"), 43200));
        return response;
    }
}
