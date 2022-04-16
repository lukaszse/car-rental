package org.lukaszse.carRental.model.dto;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRatesNBPtable {
    private String table;
    private String no;
    private  String effectiveDate;

    @Embedded
    private List<CurrencyRates> rates;
}
