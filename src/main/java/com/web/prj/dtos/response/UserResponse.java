package com.web.prj.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.prj.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String accountId;
    private String email;
    private String phone;
    private String name;
    private Gender gender;
    private LocalDate birth;
    private String avatar;
    private String address;
    private boolean active;
    private String roleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
