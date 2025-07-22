package com.web.prj.controllers;

import com.web.prj.dtos.request.LoginRequest;
import com.web.prj.dtos.request.OtpRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.GoogleResponse;
import com.web.prj.dtos.response.LoginResponse;
import com.web.prj.services.auth.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final OtpService authService;
    public AuthController(
            OtpService authService
    ) {
        this.authService = authService;
    }

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
}
