package org.lukaszse.carRental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Reservation {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_name")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    private LocalDate reservationDate;

    @NotNull(message = "Field Date From must not be empty")
    private LocalDate dateFrom;

    @NotNull(message = "Field Date To must not be empty")
    private LocalDate dateTo;

    @NotNull(message = "Field Price must not be null")
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal totalCost;

    private Boolean rented;

    public Reservation(final User user,
                       final Car car,
                       final LocalDate dateFrom,
                       final LocalDate dateTo,
                       final BigDecimal totalCost) {
        this.user = user;
        this.car = car;
        this.reservationDate = LocalDate.now();
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.totalCost = totalCost;
    }
}
