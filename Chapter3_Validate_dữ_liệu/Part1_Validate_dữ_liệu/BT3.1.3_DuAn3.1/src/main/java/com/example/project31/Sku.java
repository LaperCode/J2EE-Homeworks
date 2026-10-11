package com.example.project31;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = SkuValidator.class)
@Target({
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sku {

    String message() default "Invalid SKU format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}