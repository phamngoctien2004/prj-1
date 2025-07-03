package com.web.prj.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VietNamPhoneValidator implements ConstraintValidator<VietNamPhone, String> {
    private static final String PHONE_REGEX = "^(\\+84|0)[3|5|7|8|9][0-9]{8}$";

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if(phone == null){
            return false;
        }
        return phone.matches(PHONE_REGEX);
    }

}
