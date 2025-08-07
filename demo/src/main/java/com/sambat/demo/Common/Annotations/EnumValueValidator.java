package com.sambat.demo.Common.Annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValueValidator implements ConstraintValidator<ValidEnum, String> {
    private List<String> acceptedValues;

    // method to validate
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // when null use @NotNull to handle it, and this only work when have value
        if(value == null){

        }
        return acceptedValues.contains(value.toUpperCase());
    }

    // happen when initially
    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        acceptedValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name).collect(Collectors.toList());
    }
}
