package ru.tanaxxt.currencysystem.services

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.tanaxxt.currencysystem.configs.CurrencyConfig
import ru.tanaxxt.currencysystem.models.CurrencyRateDto

@Component
@EnableConfigurationProperties(CurrencyConfig::class)
class CbrClient(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
    private val config: CurrencyConfig
) {
    fun fetchRates(): List<CurrencyRateDto> {
        val response = restTemplate.getForObject(config.cbApiUrl, String::class.java)
        var result = mutableListOf<CurrencyRateDto>()

        try {
            val root = objectMapper.readTree(response)
            val valuteNode = root.get("Valute")
            valuteNode?.fields()?.forEach { (key, value) ->
                val code = value.get("CharCode").asText()
                val name = value.get("Name").asText()
                val rate = value.get("Value").asDouble()
                val previous = value.get("Previous").asDouble()
                result.add(CurrencyRateDto(code, name, rate, previous))
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to parse CBR response", e)
        }

        return result
    }
}