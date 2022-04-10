package org.lukaszse.carRental.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class TimePeriod {

    @Nullable
    private final LocalDate DateFrom;
    @Nullable
    private final LocalDate DateTo;
}
