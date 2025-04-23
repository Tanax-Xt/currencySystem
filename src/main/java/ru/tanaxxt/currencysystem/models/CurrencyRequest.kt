package ru.tanaxxt.currencysystem.models

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import ru.tanaxxt.currencysystem.entities.Currency

data class CurrencyRequest(
    @JsonProperty(value = "name", required = true)
    @field:NotBlank(message = "Name is required")
    val name: String,

    @JsonProperty(value = "baseCurrency", required = true)
    @field:NotBlank(message = "Base currency is required")
    val baseCurrency: String,

    @JsonProperty(value = "priceChangeRange", required = true)
    @field:NotBlank(message = "Price change range is required")
    val priceChangeRange: String,

    @JsonProperty("description")
    val description: String?

) {
    fun toCurrency(): Currency {
        val currency = Currency(
            name = name,
            baseCurrency = baseCurrency,
            priceChangeRange = priceChangeRange,
            description = description
        )

        return currency
    }
}