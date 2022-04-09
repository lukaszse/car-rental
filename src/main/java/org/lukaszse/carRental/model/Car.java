package org.lukaszse.carRental.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @Positive(message = "Cost per day must be positive number")
    @NotNull(message = "Cost per day not be empty.")
    private BigDecimal costPerDay;
}
