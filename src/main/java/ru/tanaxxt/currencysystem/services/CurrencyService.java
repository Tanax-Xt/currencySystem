package ru.tanaxxt.currencysystem.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tanaxxt.currencysystem.entities.Currency;
import ru.tanaxxt.currencysystem.repositories.CurrencyRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    private Currency updateCurrencyParams(Currency currencyToUpdate, Currency newCurrency) {
        currencyToUpdate.setName(newCurrency.getName());
        currencyToUpdate.setBaseCurrency(newCurrency.getBaseCurrency());
        currencyToUpdate.setPriceChangeRange(newCurrency.getPriceChangeRange());
        currencyToUpdate.setDescription(newCurrency.getDescription());
        return currencyToUpdate;
    }

    @Transactional(readOnly = true)
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findByIsDeletedFalse();
    }

    @Transactional(readOnly = true)
    public Currency getCurrencyById(UUID id) {
        return currencyRepository.findByIdAndIsDeletedFalse(id).orElse(null);
    }

    @Transactional
    public Currency addCurrency(Currency currency) {
        Currency existingCurrency = currencyRepository.findByNameOrBaseCurrency(currency.getName(),
                currency.getBaseCurrency()).orElse(null);
        if (existingCurrency != null) {
            if (!existingCurrency.isDeleted()) {
                return null;
            }
            existingCurrency.setDeleted(false);
            return currencyRepository.save(updateCurrencyParams(existingCurrency, currency));
        }
        return currencyRepository.save(currency);
    }

    @Transactional
    public Currency updateCurrency(UUID id, Currency currency) {
        Currency currencyToUpdate = currencyRepository.findByIdAndIsDeletedFalse(id).orElse(null);
        if (currencyToUpdate == null) {
            return null;
        }
        return currencyRepository.save(updateCurrencyParams(currencyToUpdate, currency));
    }

    @Transactional
    public boolean deleteCurrency(UUID id) {
        Currency currencyToDelete = currencyRepository.findByIdAndIsDeletedFalse(id).orElse(null);
        if (currencyToDelete == null) {
            return false;
        }
        currencyToDelete.setDeleted(true);
        currencyRepository.save(currencyToDelete);

        return true;
    }
}
