package org.lukaszse.carRental.customValidator

import org.lukaszse.carRental.model.TimePeriod
import spock.lang.Specification

import javax.validation.ConstraintValidatorContext
import java.time.LocalDate

class TimePeriodValidatorTest extends Specification {

    TimePeriodValidator timePeriodValidator = new TimePeriodValidator();
    ConstraintValidatorContext constraintValidatorContext = Mock()

    def "should validate TimePeriod correctly"() {

        expect: 'should return correct validation result'
        timePeriodValidator.isValid(timeperiod, constraintValidatorContext) == expectedResult

        where:
        timeperiod                                                                || expectedResult
        TimePeriod.of(LocalDate.now().plusDays(2), LocalDate.now().plusDays(3))   || true
        TimePeriod.of(LocalDate.now().plusDays(5), LocalDate.now().plusDays(3))   || false
        TimePeriod.of(LocalDate.now().minusDays(5), LocalDate.now().plusDays(3))  || false
        TimePeriod.of(LocalDate.now().minusDays(5), LocalDate.now().minusDays(3)) || false
        TimePeriod.of(LocalDate.now().plusDays(3), LocalDate.now().plusDays(3))   || true
    }
}
