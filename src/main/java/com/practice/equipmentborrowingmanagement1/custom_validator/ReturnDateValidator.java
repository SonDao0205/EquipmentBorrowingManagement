package com.practice.equipmentborrowingmanagement1.custom_validator;

import com.practice.equipmentborrowingmanagement1.model.dto.EquipmentFormRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReturnDateValidator implements ConstraintValidator<ReturnDateValid, EquipmentFormRequest> {
    @Override
    public boolean isValid(EquipmentFormRequest request, ConstraintValidatorContext context) {
        if (request.getBorrowDate() == null || request.getReturnDate() == null) {
            return true;
        }

        boolean isValid = request.getReturnDate().isAfter(request.getBorrowDate());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("returnDate")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
