package ru.tanaxxt.currencysystem.jobs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tanaxxt.currencysystem.configs.CurrencyConfig;
import ru.tanaxxt.currencysystem.models.CurrencyRateDto;
import ru.tanaxxt.currencysystem.services.CbrClient;
import ru.tanaxxt.currencysystem.entities.Currency;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tanaxxt.currencysystem.services.CurrencyService;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(CurrencyConfig.class)
public class CurrencyRateJob {

    private final CbrClient cbrClient;
    private final CurrencyService currencyService;

    private Stream<CurrencyRateDto> fetchRates() {
        return cbrClient.fetchRates().stream();
    }

    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Map<String, Double> getCodesAndRanges() {
        String regex = "([^%])+";
        Pattern pattern = Pattern.compile(regex);

        return currencyService.getAllCurrencies()
                .stream()
                .filter(currency -> !currency.isDeleted())
                .filter(currency -> {
                    Matcher matcher = pattern.matcher(currency.getPriceChangeRange());
                    matcher.find();
                    return isNumber(matcher.group(0));
                })
                .collect(Collectors
                        .toMap(Currency::getBaseCurrency, currency -> {
                                    Matcher matcher = pattern.matcher(currency.getPriceChangeRange());
                                    matcher.find();
                                    return Double.parseDouble(matcher.group(0));
                                }
                        ));
    }

    private Stream<CurrencyRateDto> filterRates(Map<String, Double> codes, Stream<CurrencyRateDto> rates) {
        return rates
                .filter(r -> codes.containsKey(r.getCode()))
                .filter(r -> codes.get(r.getCode()) >= 0 ?
                        (100 * (r.getValue() / r.getPrevious() - 1) >= codes.get(r.getCode()))
                        : (100 * (r.getValue() / r.getPrevious() - 1) <= codes.get(r.getCode())));
    }

    private void prettyPrintRates(CurrencyRateDto currencyRateDto) {
        double percent = 100 * (currencyRateDto.getValue() / currencyRateDto.getPrevious() - 1);
        if (percent >= 0) {
            System.out.printf("❗Курс \"%s\" вырос на %.2f%%%n", currencyRateDto.getName(), percent);
        } else {
            System.out.printf("❗Курс \"%s\" упал на %.2f%%%n", currencyRateDto.getName(), Math.abs(percent));
        }
    }

    @PostConstruct
    @Scheduled(cron = "${currency-tracker.cb-api-job-cron}")
    public void checkRates() {
        Map<String, Double> codes = getCodesAndRanges();

        Stream<CurrencyRateDto> rates = fetchRates();

        filterRates(codes, rates).forEach(this::prettyPrintRates);
    }
}