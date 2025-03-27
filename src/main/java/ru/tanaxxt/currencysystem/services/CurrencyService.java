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
        return currencyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Currency getCurrencyById(UUID id) {
        return currencyRepository.findById(id).orElse(null);
    }

    @Transactional
    public Currency addCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    @Transactional
    public Currency updateCurrency(UUID id, Currency currency) {
        Currency currencyToUpdate = currencyRepository.findById(id).orElse(null);
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
        currencyRepository.deleteById(id);
    }
}
