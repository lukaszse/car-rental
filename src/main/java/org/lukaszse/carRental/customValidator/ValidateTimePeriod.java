package org.lukaszse.carRental.customValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimePeriodValidator.class)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateTimePeriod {
    String message() default "Invalid time period. Please check if chosen dates are correct!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
