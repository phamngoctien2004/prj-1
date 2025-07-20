package com.web.prj.services.impl;

import com.web.prj.Helpers.HttpHelper;
import com.web.prj.Helpers.OtpHelper;
import com.web.prj.dtos.UserDTO;
import com.web.prj.dtos.request.GoogleRequest;
import com.web.prj.dtos.request.LoginRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.GoogleResponse;
import com.web.prj.dtos.response.LoginResponse;
import com.web.prj.entities.User;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.mappers.mapper.UserMapper;
import com.web.prj.services.cores.AuthService;
import com.web.prj.services.cores.UserService;
import com.web.prj.services.helpers.OtpService;
import com.web.prj.services.impl.EmailServiceImpl;
import com.web.prj.services.impl.JwtServiceImpl;
import com.web.prj.services.impl.UserServiceImpl;
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
public class AuthServiceImpl implements OtpService {
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
    private final JwtServiceImpl jwtServiceImpl;
    private final EmailServiceImpl emailService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public LoginResponse login(LoginRequest request) {
        String key = "otp:" + request.getEmail();
        Object value = redisTemplate.opsForValue().get(key);

        // kieerm tra otp ko hop le
        if (value == null || !OtpHelper.validateTOTP(value.toString(), request.getOtp())) {
            throw new AppException(ErrorCode.OTP_INVALID);
        }
        Optional<User> oUser = userService.findByEmail(request.getEmail());

        User user = oUser.orElseGet(() -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(request.getEmail());
            return userService.createUser(userDTO);
        });
        redisTemplate.delete(key);

        String at = jwtServiceImpl.generate(user.getEmail(), "USER", 5);
        String rt = jwtServiceImpl.generate(user.getEmail(), "USER", 43200);

        return LoginResponse.builder()
                .accessToken(at)
                .refreshToken(rt)
                .build();
    }

    @Override
    public String sendOtp(String email) {
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

        return otp;
    }

    @Override
    public String generateLink() {
        return UriComponentsBuilder
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
    public LoginResponse loginGoogle(GoogleResponse userInfo) {
        Optional<User> oUser = userService.findByEmail(userInfo.getEmail());

        User user = oUser.orElseGet(() -> {
            UserDTO u = new UserDTO();
            u.setEmail(userInfo.getEmail());
            u.setName(userInfo.getName());
            u.setAvatar(userInfo.getPicture());
            return userService.createUser(u);
        });

        String at = jwtServiceImpl.generate(user.getEmail(), "USER", 5);
        String rt = jwtServiceImpl.generate(user.getEmail(), "USER", 43200);

        return LoginResponse.builder()
                .accessToken(at)
                .refreshToken(rt)
                .build();

    }

    @Override
    public void saveOtpRedis(String email, String otp, String secretKey) {
        String key = "otp:" + email;
        redisTemplate.opsForValue().set(key, secretKey, Duration.ofMinutes(5));
    }

    @Override
    public void sendOtpMail(String email, String otp) {
        String content = OtpHelper.emailContent.replace("123456", otp);
        emailService.sendEmail(email, content, "Mã xác thực otp");
    }

}
