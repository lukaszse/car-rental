package org.lukaszse.carRental.service

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class TotalCostSpec extends Specification {

    @Unroll
    def "should check if data ranges equals correctly - test #no"() {

        given: "Prepare dates"
        def localDateFrom = LocalDate.parse(dateFrom)
        def localDateTo = LocalDate.parse(dateTo)

        when: "Try to calculate cost"
        def overlap = ReservationService.calculateTotalCost(localDateFrom, localDateTo, costPerDay as BigDecimal)

        then: "Cost should be calculated correctly"
        overlap == expectedResult

        where:
        no | dateFrom       | dateTo         |  costPerDay  || expectedResult
        1  | "2018-05-20"   | "2018-06-02"   | 100          || 1300
        2  | "2019-03-10"   | "2019-05-22"   | 70           || 5110
        3  | "2018-03-20"   | "2018-03-22"   | 200          || 400
        4  | "2000-09-01"   | "2000-09-30"   | 50           || 1450
    }
}
