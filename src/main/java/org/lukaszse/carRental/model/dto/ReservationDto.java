package org.lukaszse.carRental.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Slf4j
@NoArgsConstructor
public class ReservationDto {

    private Integer id;

    @NotNull(message = "Field must not be empty")
    private String userName;

    @NotNull(message = "Field must not be empty")
    private Integer carId;

    @NotBlank(message = "Field Date From must not be empty")
    private LocalDate dateFrom;

    @NotBlank(message = "Field Date To must not be empty")
    private LocalDate dateTo;

    private Boolean rented;
}
