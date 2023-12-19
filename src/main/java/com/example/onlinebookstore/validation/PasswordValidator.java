package com.example.onlinebookstore.validation;

import com.example.onlinebookstore.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<FieldMatch, Object> {

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        UserRegistrationRequestDto userRegistrationRequestDto = (UserRegistrationRequestDto) object;
        return userRegistrationRequestDto.getPassword()
                .equals(userRegistrationRequestDto.getRepeatPassword());
    }
}
