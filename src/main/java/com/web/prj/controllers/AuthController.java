package com.web.prj.controllers;

import com.web.prj.dtos.request.LoginRequest;
import com.web.prj.dtos.request.OtpRequest;
import com.web.prj.dtos.response.GoogleResponse;
import com.web.prj.services.interfaces.IAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/google-url")
    public ResponseEntity<?> generateLinkGoogleAuth(){
        return ResponseEntity.ok(authService.generateLink());
    }
    @GetMapping("/google/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code){
        GoogleResponse googleResponse = authService.getUserInfo(code);
        return ResponseEntity.ok(authService.loginGoogle(googleResponse));
    }
}
