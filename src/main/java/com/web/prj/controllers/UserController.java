package com.web.prj.controllers;

import com.web.prj.dtos.UserDTO;
import com.web.prj.dtos.response.ApiResponse;
import com.web.prj.dtos.response.PageResponse;
import com.web.prj.entities.User;
import com.web.prj.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> myInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok(userService.myInfo(email));
    }

    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable, @RequestParam("filter") Optional<String> filter){
        ApiResponse<PageResponse<List<UserDTO>>> response = ApiResponse.<PageResponse<List<UserDTO>>>builder()
                .code("200")
                .message("Lấy dữ liệu thành công")
                .data(userService.findAllByPageAndFilter(pageable, filter))
                .build();

        return ResponseEntity.ok(response);
    }

}
