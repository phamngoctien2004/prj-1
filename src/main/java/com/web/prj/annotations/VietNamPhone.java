package com.web.prj.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = VietNamPhoneValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface VietNamPhone {
    String message() default "Số điện thoại không hợp lệ (phải là số Việt Nam)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
