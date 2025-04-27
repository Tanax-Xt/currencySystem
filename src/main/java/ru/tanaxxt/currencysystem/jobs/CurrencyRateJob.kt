package ru.tanaxxt.currencysystem.jobs

import jakarta.annotation.PostConstruct
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.tanaxxt.currencysystem.configs.CurrencyConfig
import ru.tanaxxt.currencysystem.entities.Currency
import ru.tanaxxt.currencysystem.models.CurrencyRateDto
import ru.tanaxxt.currencysystem.services.CbrClient
import ru.tanaxxt.currencysystem.services.CurrencyService
import java.util.stream.Collectors
import java.util.stream.Stream


private fun isNumber(str: String): Boolean {
    try {
        str.toDouble()
        return true
    } catch (e: NumberFormatException) {
        return false
    }
}

private fun calculatePercentChange(previous: Double, current: Double): Double {
    return 100 * (current / previous - 1)
}

@Component
@EnableConfigurationProperties(CurrencyConfig::class)
class CurrencyRateJob(
    private val cbrClient: CbrClient,
    private val currencyService: CurrencyService
) {
    private fun fetchRates() = cbrClient.fetchRates().stream()

    private fun getCodesAndRanges(): Map<String, Double> {
        return currencyService.getAllCurrencies()
            .stream()
            .filter { currency: Currency -> !currency.isDeleted }
            .filter { currency: Currency -> isNumber(currency.priceChangeRange.split('%')[0]) }
            .collect(
                Collectors
                    .toMap(
                        Currency::baseCurrency,
                        { currency: Currency -> currency.priceChangeRange.split('%')[0].toDouble() }
                    )
            )
    }

    private fun filterRates(codes: Map<String, Double>, rates: Stream<CurrencyRateDto>): Stream<CurrencyRateDto> {
        return rates
            .filter { r: CurrencyRateDto -> codes.containsKey(r.code) }
            .filter { r: CurrencyRateDto ->
                if (codes[r.code]!! >= 0)
                    calculatePercentChange(r.previous, r.value) >= codes[r.code]!!
                else
                    calculatePercentChange(r.previous, r.value) <= codes[r.code]!!
            }
    }

    private fun prettyPrintRates(currencyRateDto: CurrencyRateDto) {
        val percent = calculatePercentChange(currencyRateDto.previous, currencyRateDto.value)
        if (percent >= 0) {
            System.out.printf("❗Курс \"%s\" вырос на %.2f%%%n", currencyRateDto.name, percent)
        } else {
            System.out.printf("❗Курс \"%s\" упал на %.2f%%%n", currencyRateDto.name, Math.abs(percent))
        }
    }

    @PostConstruct
    @Scheduled(cron = "\${currency-tracker.cb-api-job-cron}")
    fun checkRates() {
        val codes = getCodesAndRanges()
        val rates = fetchRates()
        val filteredRates = filterRates(codes, rates)
        filteredRates.forEach(this::prettyPrintRates)
    }
}