package com.web.prj.controllers;

import com.web.prj.dtos.request.UserRequest;
import com.web.prj.dtos.request.LoginRequest;
import com.web.prj.dtos.request.OtpRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.GoogleResponse;
import com.web.prj.dtos.response.LoginResponse;
import com.web.prj.exceptions.AppException;
import com.web.prj.exceptions.ErrorCode;
import com.web.prj.services.auth.TokenService;
import com.web.prj.services.auth.VerifyService;
import com.web.prj.services.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final VerifyService authService;
    private final UserService userService;
    private final TokenService tokenService;
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody @Valid OtpRequest request) {
        String response = authService.sendOtp(request.getEmail());
        return ResponseEntity.ok(
                new ApiResponse<>(response, "OTP sent successfully")
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("refresh_token", response.getRefreshToken())
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiResponse<LoginResponse>(response, "Login successful"));
    }

    @PostMapping("register-info")
    public ResponseEntity<?> registerInfo(@RequestBody @Valid UserRequest request) {
        userService.updateUser(request);
        return ResponseEntity.ok("2");
    }
    @GetMapping("/google-url")
    public ResponseEntity<?> generateLinkGoogleAuth() {
        return ResponseEntity.ok(new ApiResponse<>(authService.generateLink(), "Google authentication URL generated successfully"));
    }

    @GetMapping("/google/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        GoogleResponse googleResponse = authService.getUserInfo(code);
        LoginResponse loginResponse = authService.loginGoogle(googleResponse);
        ResponseCookie cookie = ResponseCookie.from("refresh_token", loginResponse.getRefreshToken())
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new ApiResponse<LoginResponse>(loginResponse, "Login successful"));
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
       Cookie[] cookies = request.getCookies();
       String accessToken = null;
       for(Cookie cookie: cookies){
           if(cookie.getName().equals("refresh_token")){
               String refreshToken = cookie.getValue();
                accessToken = tokenService.refresh(refreshToken);
           }
       }
         if (accessToken == null) {
             throw new AppException(ErrorCode.AUTH_FAILED);
         }

        return ResponseEntity.ok(
                new ApiResponse<>(accessToken, "Token refreshed successfully")
        );
    }
}
