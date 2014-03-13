package com.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.util.CustomDateValidator;

@Target({ ElementType.METHOD, ElementType.FIELD , ElementType.PARAMETER})
@Constraint(validatedBy = CustomDateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateTime {

	String message() default "{ValidDateTime}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
