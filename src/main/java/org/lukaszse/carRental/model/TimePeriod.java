package org.lukaszse.carRental.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class TimePeriod {

    @NotNull(message = "Date from cannot be null")
    private LocalDate dateFrom;
    @NotNull(message = "Date to cannot be null")
    private LocalDate dateTo;

    public TimePeriod(final String dateFrom, final String dateTo) {
        this.dateFrom = toLocalDate(dateFrom);
        this.dateTo = toLocalDate(dateTo);
    }

    public void setDateFrom(final String dateFrom) {
        this.dateFrom = toLocalDate(dateFrom);
    }

    public void setDateTo(final String dateTo) {
        this.dateTo = toLocalDate(dateTo);
    }

    private static LocalDate toLocalDate(final String dateString) {
        return dateString != null && !dateString.isEmpty() ? LocalDate.parse(dateString) : null;
    }
}
