package com.clevercattv.table.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RegexValidationRealisation implements ConstraintValidator<RegexValidator, String> {

    @Override
    public void initialize(RegexValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }

}
