package ru.tanaxxt.currencysystem.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.tanaxxt.currencysystem.entities.Currency
import ru.tanaxxt.currencysystem.repositories.CurrencyRepository
import java.util.UUID

@Service
class CurrencyService(val currencyRepository: CurrencyRepository) {
    private fun updateCurrencyParams(currencyToUpdate: Currency, newCurrency: Currency): Currency {
        currencyToUpdate.name = newCurrency.name
        currencyToUpdate.baseCurrency = newCurrency.baseCurrency
        currencyToUpdate.priceChangeRange = newCurrency.priceChangeRange
        currencyToUpdate.description = newCurrency.description
        return currencyToUpdate
    }

    @Transactional(readOnly = true)
    fun getAllCurrencies() = currencyRepository.findByIsDeletedFalse()

    @Transactional(readOnly = true)
    fun getCurrencyById(id: UUID) = currencyRepository.findByIdAndIsDeletedFalse(id).orElse(null)

    @Transactional
    fun addCurrency(currency: Currency): Currency? {
        val existingCurrency =
            currencyRepository.findByNameOrBaseCurrency(currency.name, currency.baseCurrency).orElse(null)
                ?: return currencyRepository.save(currency)

        if (existingCurrency.isDeleted) {
            existingCurrency.isDeleted = false
            return currencyRepository.save(updateCurrencyParams(existingCurrency, currency))
        }
        return null
    }

    @Transactional
    fun updateCurrency(id: UUID, currency: Currency): Currency? {
        val existingCurrency = getCurrencyById(id) ?: return null
        return currencyRepository.save(updateCurrencyParams(existingCurrency, currency))
    }

    @Transactional
    fun deleteCurrency(id: UUID): Boolean {
        val existingCurrency = getCurrencyById(id) ?: return false
        existingCurrency.isDeleted = true
        currencyRepository.save(existingCurrency)
        return true
    }
}