package ru.tanaxxt.currencysystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tanaxxt.currencysystem.entities.Currency;
import ru.tanaxxt.currencysystem.requests.CurrencyRequest;
import ru.tanaxxt.currencysystem.services.CurrencyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/currencies")
@Validated
@RequiredArgsConstructor
@Tag(name = "Currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping("")
    @Operation(summary = "Получить список отслеживаемых валют")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос, возвращает список валют"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public List<Currency> getCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @PostMapping("")
    @Operation(summary = "Добавить новую валюту для отслеживания")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Валюта успешно добавлена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные в запросе"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<Currency> postCurrency(@Valid @RequestBody CurrencyRequest currencyRequest) {
        Currency createdCurrency = currencyService.addCurrency(currencyRequest.toCurrency());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCurrency);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о конкретной валюте")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный запрос, возвращает данные валюты"),
            @ApiResponse(responseCode = "404", description = "Валюта не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<Currency> getCurrency(@PathVariable UUID id) {
        Currency currency = currencyService.getCurrencyById(id);
        if (currency == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(currency);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные валюты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные валюты успешно обновлены"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные в запросе"),
            @ApiResponse(responseCode = "404", description = "Валюта не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<Currency> putCurrency(@PathVariable UUID id, @Valid @RequestBody CurrencyRequest currencyRequest) {
        Currency currency = currencyService.updateCurrency(id, currencyRequest.toCurrency());
        if (currency == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(currency);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить валюту из отслеживания")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Валюта успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Валюта не найдена"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<String> deleteCurrency(@PathVariable UUID id) {
        Currency currency = currencyService.getCurrencyById(id);
        if (currency == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        currencyService.deleteCurrency(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}


