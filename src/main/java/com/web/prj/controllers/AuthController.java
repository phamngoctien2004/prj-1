package com.web.prj.controllers;

import com.web.prj.dtos.request.LoginRequest;
import com.web.prj.dtos.request.OtpRequest;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.GoogleResponse;
import com.web.prj.dtos.response.LoginResponse;
import com.web.prj.services.interfaces.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IAuthService authService;

    public AuthController(
            IAuthService authService
    ) {
        this.authService = authService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody @Valid OtpRequest request) {

        return ResponseEntity.ok(authService.sendOtp(request.getEmail()));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody @Valid LoginRequest request) {
        ApiResponse<LoginResponse> response = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("refresh_token", response.getData().getRefreshToken())
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }

    @GetMapping("/google-url")
    public ResponseEntity<?> generateLinkGoogleAuth() {
        return ResponseEntity.ok(authService.generateLink());
    }

    @GetMapping("/google/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        GoogleResponse googleResponse = authService.getUserInfo(code);
        ApiResponse<LoginResponse> response = authService.loginGoogle(googleResponse);
        ResponseCookie cookie = ResponseCookie.from("refresh_token", response.getData().getRefreshToken())
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }
}
