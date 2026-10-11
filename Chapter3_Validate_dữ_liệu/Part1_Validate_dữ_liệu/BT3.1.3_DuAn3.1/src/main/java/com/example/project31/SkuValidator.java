package com.example.project31;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SkuValidator
        implements ConstraintValidator<Sku, String> {

    private static final Pattern SKU_PATTERN =
            Pattern.compile("^[A-Z0-9]{8}$");

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context) {

        return value == null
                || SKU_PATTERN.matcher(value).matches();
    }
}