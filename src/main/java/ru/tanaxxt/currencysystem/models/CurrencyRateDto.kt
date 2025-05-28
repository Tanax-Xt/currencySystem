package ru.tanaxxt.currencysystem.models

data class CurrencyRateDto(
    val code: String,
    val name: String,
    val value: Double,
    val previous: Double
)
