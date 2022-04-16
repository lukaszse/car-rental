package org.lukaszse.carRental.model.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CurrencyRates {
    private String currency;
    private String code;
    private Double mid;
}
