package ru.tanaxxt.currencysystem.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tanaxxt.currencysystem.entities.Currency;
import ru.tanaxxt.currencysystem.repositories.CurrencyRepository;
import ru.tanaxxt.currencysystem.requests.CurrencyRequest;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency getCurrencyById(UUID id) {
        return currencyRepository.findById(id).orElse(null);
    }


    public Currency addCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

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

    public void deleteCurrency(UUID id) {
        currencyRepository.deleteById(id);
    }
}
