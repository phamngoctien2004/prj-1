package com.web.prj.dtos;

import com.web.prj.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO extends BaseDTO{
    private Long id;
    @NotBlank(message = "Mã người dùng không được trống")
    private String accountId;
    @NotBlank
    @Email(message = "Email không hợp lệ")
    private String email;

//    theem validator check phone
    private String phone;

    @NotBlank(message = "Vui lòng nhập tên người dùng")
    private String name;

    @NotNull(message = "Vui lòng chọn giới tính")
    private Gender gender;

    private LocalDate birth;
    private String avatar;
    private String roleName;
}
