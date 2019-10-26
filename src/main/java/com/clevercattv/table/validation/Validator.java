package com.clevercattv.table.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Validator {

    private static javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if(!violations.isEmpty()) {
            throw new ValidationException(
                    object.getClass().getName() +
                    violations.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.toList())
                            .toString()
            );
        }
    }

}
