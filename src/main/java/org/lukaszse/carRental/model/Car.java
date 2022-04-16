package org.lukaszse.carRental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "registrationNo")
@Entity
@NoArgsConstructor
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;

    @NotBlank(message = "Field model must not be empty.")
    private String registrationNo;

    @NotBlank(message = "Field manufacturer must not be empty.")
    private String manufacturer;

    @NotBlank(message = "Field model must not be empty.")
    private String model;

    @NotBlank(message = "Field fuelType must not be empty.")
    private String fuelType;

    @NotBlank(message = "Field type must not be empty.")
    private String type;

    @Positive(message = "Capacity Value must be positive")
    @NotNull(message = "Field Property must not be empty")
    private Integer engineCapacity;

    @Positive(message = "Passenger number must be positive number")
    @NotNull(message = "Field Phone must not be empty")
    private Integer passengerNumber;

    @NotBlank(message = "Field City must not be empty.")
    private String description;

    @NotNull(message = "Cost per day not be empty.")
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal costPerDay;
}
