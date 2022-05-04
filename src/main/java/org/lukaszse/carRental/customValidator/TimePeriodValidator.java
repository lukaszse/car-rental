package org.lukaszse.carRental.customValidator;

import org.lukaszse.carRental.model.TimePeriod;
import org.springframework.lang.Nullable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class TimePeriodValidator implements ConstraintValidator<ValidateTimePeriod, TimePeriod> {

    @Override
    public void initialize(ValidateTimePeriod timePeriod) {
    }

    @Override
    public boolean isValid(@Nullable TimePeriod timePeriod, ConstraintValidatorContext constraintValidatorContext) {

        return timePeriod == null
                || ((timePeriod.getDateFrom() == null) && timePeriod.getDateTo() == null)
                || ((timePeriod.getDateFrom() != null && timePeriod.getDateTo() != null)
                && (timePeriod.getDateFrom().isBefore(timePeriod.getDateTo()) || timePeriod.getDateFrom().isEqual(timePeriod.getDateTo()))
                && (LocalDate.now().isBefore(timePeriod.getDateFrom()) || LocalDate.now().isEqual(timePeriod.getDateFrom()))
                && (LocalDate.now().isBefore(timePeriod.getDateTo()) || LocalDate.now().isAfter(timePeriod.getDateTo())));
    }
}
