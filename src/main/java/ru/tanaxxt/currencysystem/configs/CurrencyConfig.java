package ru.tanaxxt.currencysystem.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "currency-tracker")
public class CurrencyConfig {
    private String cbApiUrl;
}