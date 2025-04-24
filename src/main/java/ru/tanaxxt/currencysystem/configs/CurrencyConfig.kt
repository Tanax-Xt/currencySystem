package ru.tanaxxt.currencysystem.configs

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "currency-tracker")
data class CurrencyConfig(val cbApiUrl: String)
