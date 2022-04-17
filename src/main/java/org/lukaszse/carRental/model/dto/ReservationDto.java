package org.lukaszse.carRental.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.lukaszse.carRental.model.Car;
import org.lukaszse.carRental.model.Reservation;
import org.lukaszse.carRental.model.TimePeriod;
import org.lukaszse.carRental.model.User;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(staticName = "of")
public class ReservationDto {

    private Integer id;
    private String userName;
    private User user;
    private Integer carId;
    private Car car;
    private LocalDate reservationDate;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BigDecimal totalCost;
    private Boolean rented;

    public static ReservationDto of(final Reservation reservation) {
        return of(reservation.getId(),
                reservation.getUser().getUserName(),
                reservation.getUser(),
                reservation.getCar().getId(),
                reservation.getCar(),
                reservation.getReservationDate(),
                reservation.getDateFrom(),
                reservation.getDateTo(),
                reservation.getTotalCost(),
                reservation.getRented());
    }

    public static ReservationDto of(final String userName,
                                    final int carId,
                                    final Car car,
                                    final TimePeriod timePeriod,
                                    final BigDecimal totalCost) {
        return ReservationDto.builder()
                .userName(userName)
                .carId(carId)
                .car(car)
                .dateFrom(timePeriod.getDateFrom())
                .dateTo(timePeriod.getDateTo())
                .totalCost(totalCost)
                .build();
    }
}
