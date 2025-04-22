package ru.tanaxxt.currencysystem.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrencyRateDto {
    private String code;
    private String name;
    private double value;
    private double previous;
}
