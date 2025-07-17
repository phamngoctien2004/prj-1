package com.web.prj.services;

import com.web.prj.Helpers.HttpHelper;
import com.web.prj.Helpers.OtpHelper;
import com.web.prj.dtos.request.GoogleRequest;
import com.web.prj.dtos.request.LoginRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.GoogleResponse;
import com.web.prj.dtos.response.LoginResponse;
import com.web.prj.entities.User;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.services.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.*;

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

        // kieerm tra otp ko hop le
        if (value == null || !OtpHelper.validateTOTP(value.toString(), request.getOtp())) {
            throw new AppException(ErrorCode.OTP_INVALID);
        }
        Optional<User> oUser = userService.findByEmail(request.getEmail());
        User user = oUser.orElseGet(() -> {
            User u = new User();
            u.setEmail(request.getEmail());
            return userService.createUser(u);
        });
        redisTemplate.delete(key);

        return ApiResponse.<LoginResponse>builder()
                .success(true)
                .data(toLoginResponse(user))
                .message("Đăng nhập thành công")
                .build();
    }

    @Override
    public ApiResponse<String> sendOtp(String email) {
        // kiem tra xem otp se duoc gui hay khong
        String key = "otp:" + email;
        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            throw new AppException(ErrorCode.OTP_CANNOT_RESEND);
        }
        // sinh totp
        String secretKey = OtpHelper.generateSecretKey();
        String generatedTime = String.valueOf(System.currentTimeMillis());
        String otp = OtpHelper.generateTOTP(secretKey, generatedTime);

        saveOtpRedis(email, otp, secretKey);
        sendOtpMail(email, otp);

        return ApiResponse.<String>builder()
                .data(generatedTime)
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

        return new ApiResponse<>("200", url, null, true, "Tạo link thành công");
    }

    @Override
    public GoogleResponse getUserInfo(String code) {
        GoogleRequest googleRequest = GoogleRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri(redirectURI)
                .grantType("authorization_code")
                .build();
        GoogleResponse googleResponse = HttpHelper.post(googleRequest);
        String accessToken = googleResponse.getAccess_token();

        return HttpHelper.get(accessToken);
    }

    @Override
    public ApiResponse<LoginResponse> loginGoogle(GoogleResponse userInfo) {
        Optional<User> oUser = userService.findByEmail(userInfo.getEmail());

        User user = oUser.orElseGet(() -> {
            User u = new User();
            u.setEmail(userInfo.getEmail());
            u.setName(userInfo.getName());
            u.setAvatar(userInfo.getPicture());
            return userService.createUser(u);
        });

        return ApiResponse.<LoginResponse>builder()
                .data(toLoginResponse(user))
                .success(true)
                .message("Đăng nhập thành công")
                .build();
    }

    public void saveOtpRedis(String email, String otp, String secretKey) {
        String key = "otp:" + email;
        redisTemplate.opsForValue().set(key, secretKey, Duration.ofMinutes(5));
    }

    public void sendOtpMail(String email, String otp) {
        String content = OtpHelper.emailContent.replace("123456", otp);
        emailService.sendEmail(email, content, "Mã xác thực otp");
    }

    public LoginResponse toLoginResponse(User user) {
        LoginResponse response = new LoginResponse();
        response.setUser(userService.toDto(user));
        response.setRole(user.getRole().getRoleId());
        response.setAccessToken(jwtService.generate(user.getEmail(), "USER", 5));
        response.setRefreshToken(jwtService.generate(user.getEmail(), "USER", 43200));

        return response;
    }
}
