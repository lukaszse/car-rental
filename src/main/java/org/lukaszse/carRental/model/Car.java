package org.lukaszse.carRental.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@NoArgsConstructor
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;

    @NotBlank(message = "Field Name must not be empty.")
    private String name;

    @NotBlank(message = "Field Street must not be empty.")
    private String street;

    @Positive(message = "Property Value must be positive")
    @NotNull(message = "Field Property must not be empty")
    private Integer property;

    @NotBlank(message = "Field Post must not be empty.")
    private String post;

    @NotBlank(message = "Field City must not be empty.")
    private String city;

    @NotBlank(message = "Field Country must not be empty.")
    private String country;

    @Positive(message = "Phone must be positive number")
    @NotNull(message = "Field Phone must not be empty")
    private Long phone;
}