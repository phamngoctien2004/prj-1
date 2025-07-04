package com.web.prj.controllers;

import com.web.prj.dtos.UserDTO;
import com.web.prj.dtos.request.RegisterRequest;
import com.web.prj.services.AuthService;
import com.web.prj.services.UserService;
import com.web.prj.services.interfaces.IAuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IAuthService authService;

    public AuthController(
            IAuthService authService
            ) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> post(@RequestBody @Valid RegisterRequest request){
        authService.register(request);
        return ResponseEntity.ok("Success");
    }
}
