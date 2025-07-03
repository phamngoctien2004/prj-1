package com.web.prj.controllers;

import com.web.prj.dtos.UserDTO;
import com.web.prj.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/public")
    public ResponseEntity<List<UserDTO>> get(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/public")
    public ResponseEntity<String> post(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity.ok("Success");
    }
}
