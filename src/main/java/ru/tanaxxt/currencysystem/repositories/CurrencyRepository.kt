package ru.tanaxxt.currencysystem.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.tanaxxt.currencysystem.entities.Currency
import java.util.*

@Repository
interface CurrencyRepository: JpaRepository<Currency, UUID> {
    fun findByIdAndIsDeletedFalse(id: UUID): Optional<Currency>
    fun findByNameOrBaseCurrency(name: String, baseCurrency: String): Optional<Currency>
    fun findByIsDeletedFalse(): List<Currency>
}