package com.practice.equipmentborrowingmanagement1.custom_validator;

import com.practice.equipmentborrowingmanagement1.model.dto.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatchesValid, UserRequest> {
    @Override
    public boolean isValid(UserRequest userRequest, ConstraintValidatorContext context) {
        if (userRequest.getPassword() == null || userRequest.getConfirmPassword() == null) {
            return true;
        }

        boolean isValid = userRequest.getPassword().equals(userRequest.getConfirmPassword());

        // đẩy lỗi vào trường confirmPassword
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
