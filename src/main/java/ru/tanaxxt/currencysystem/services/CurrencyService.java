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
        Currency existingCurrency = currencyRepository.findByNameAndIsDeletedFalse(currency.getName()).orElse(null);
        if (existingCurrency != null) {
            return null;
        }
        return currencyRepository.save(currency);
    }

    @Transactional
    public Currency updateCurrency(UUID id, Currency currency) {
        Currency currencyToUpdate = currencyRepository.findByIdAndIsDeletedFalse(id).orElse(null);
        if (currencyToUpdate == null) {
            return null;
        }
        currencyToUpdate.setName(currency.getName());
        currencyToUpdate.setBaseCurrency(currency.getBaseCurrency());
        currencyToUpdate.setPriceChangeRange(currency.getPriceChangeRange());
        currencyToUpdate.setDescription(currency.getDescription());
        return currencyRepository.save(currencyToUpdate);
    }

    @Transactional
    public void deleteCurrency(UUID id) {
        Currency currencyToDelete = currencyRepository.findByIdAndIsDeletedFalse(id).orElse(null);
        if (currencyToDelete == null) {
            return;
        }
        currencyToDelete.setDeleted(true);
        currencyRepository.save(currencyToDelete);
    }
}
