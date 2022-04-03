package org.lukaszse.contractorsapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;

    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @NotNull(message = "Field Price must not be null")
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal price;

    @NotBlank(message = "Field Order Name must not be empty")
    @Column(name = "order_name")
    private String orderName;

    @NotBlank(message = "Field Order Description must not be empty")
    @Column(name = "order_description")
    private String orderDescription;

    public Reservation(Car car, BigDecimal price, String orderName, String orderDescription) {
        this.orderDate = LocalDate.now();
        this.car = car;
        this.price = price;
        this.orderName = orderName;
        this.orderDescription = orderDescription;
    }
}
