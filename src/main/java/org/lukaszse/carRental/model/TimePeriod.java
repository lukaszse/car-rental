package org.lukaszse.carRental.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class TimePeriod {

    @Nullable
    private final Instant from;
    @Nullable
    private final Instant to;
}
