package ru.tanaxxt.currencysystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
class CurrencySystemApplication

fun main(args: Array<String>) {
    runApplication<CurrencySystemApplication>(*args)
}