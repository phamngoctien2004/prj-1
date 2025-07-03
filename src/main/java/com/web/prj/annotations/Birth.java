package com.web.prj.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BirthValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Birth {
    String message() default "Tuổi phải lớn hơn 10";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
