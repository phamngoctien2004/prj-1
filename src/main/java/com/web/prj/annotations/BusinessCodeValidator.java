package com.web.prj.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class BusinessCodeValidator implements ConstraintValidator<Code, String> {

    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z]+(_[A-Z]+)*$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isBlank()){
            return false;
        }
        return CODE_PATTERN.matcher(value).matches();

    }

    @Override
    public void initialize(Code constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
