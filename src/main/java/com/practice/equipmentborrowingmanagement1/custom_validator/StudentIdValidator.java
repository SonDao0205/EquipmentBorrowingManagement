package com.practice.equipmentborrowingmanagement1.custom_validator;

import com.practice.equipmentborrowingmanagement1.model.dto.EquipmentFormRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StudentIdValidator implements ConstraintValidator<StudentIdValid, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()){
            return true;
        }

        return value.matches("^SV\\d+$");
    }
}
