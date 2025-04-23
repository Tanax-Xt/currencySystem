package ru.tanaxxt.currencysystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class CurrencySystemApplication

fun main(args: Array<String>) {
    runApplication<CurrencySystemApplication>(*args)
}