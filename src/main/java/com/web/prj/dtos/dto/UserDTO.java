package com.web.prj.dtos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.prj.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO{
    private Long id;
    private String accountId;
    @NotBlank
    @Email(message = "Email không hợp lệ")
    private String email;

//    theem validator check phone
    @Size(min = 10, max = 11, message = "Số điện thoại phải có độ dài từ 10 đến 11 ký tự")
    private String phone;

    @NotBlank(message = "Vui lòng nhập tên người dùng")
    private String name;

    @NotNull(message = "Vui lòng chọn giới tính")
    private Gender gender;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate birth;
    private String avatar;
    private String address;
    private Integer status;
    private String roleName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
