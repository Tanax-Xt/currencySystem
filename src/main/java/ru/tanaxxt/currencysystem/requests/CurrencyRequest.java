package ru.tanaxxt.currencysystem.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CurrencyRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Base currency is required")
    private String baseCurrency = "RUB";
    @NotBlank(message = "Price change range is required")
    private String priceChangeRange;
    private String description;

}


