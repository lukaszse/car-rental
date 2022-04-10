package org.lukaszse.carRental.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lukaszse.carRental.model.Car;
import org.lukaszse.carRental.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ReservationViewDto {

    private Integer id;
    private String userName;
    private Integer carId;
    private LocalDate reservationDate;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal totalCost;
    private Boolean rented;

    public static ReservationViewDto of(final Reservation reservation) {
        return ReservationViewDto.of(
                reservation.getId(),
                reservation.getUser().getUserName(),
                reservation.getCar().getId(),
                reservation.getReservationDate(),
                reservation.getDateFrom(),
                reservation.getDateTo(),
                reservation.getTotalCost(),
                reservation.getRented());
    }
}
