package com.web.prj.entities;

import com.web.prj.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String email;
    private String password;
    protected String name;
    private String avatar;
    private String phone;
    private Gender gender;
    private LocalDate birth;
    private boolean isActive;

    @ManyToOne
    private Role role;
}
