package ru.tanaxxt.currencysystem.configs

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@EnableConfigurationProperties(CurrencyConfig::class)
class ApplicationConfig {
    @Bean
    fun getObjectmapper() = ObjectMapper()

    @Bean
    fun getRestTemplate() = RestTemplate()
}