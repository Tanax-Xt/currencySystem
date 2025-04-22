package ru.tanaxxt.currencysystem.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.tanaxxt.currencysystem.entities.Currency
import ru.tanaxxt.currencysystem.models.CurrencyRequest
import ru.tanaxxt.currencysystem.services.CurrencyService
import java.util.*

@RestController
@RequestMapping("/currencies")
@Validated
@Tag(name = "Currencies")
class CurrencyController(
    private val currencyService: CurrencyService
) {

    @GetMapping
    @Operation(summary = "Получить список отслеживаемых валют")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Успешный запрос, возвращает список валют"),
            ApiResponse(responseCode = "400", description = "Некорректный запрос"),
            ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
        ]
    )
    fun getCurrencies(): List<Currency> = currencyService.allCurrencies

    @PostMapping
    @Operation(summary = "Добавить новую валюту для отслеживания")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Валюта успешно добавлена"),
            ApiResponse(responseCode = "400", description = "Некорректные данные в запросе"),
            ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
        ]
    )
    fun postCurrency(@Valid @RequestBody currencyRequest: CurrencyRequest): ResponseEntity<Currency> {
        val createdCurrency = currencyService.addCurrency(currencyRequest.toCurrency())
            ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCurrency)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о конкретной валюте")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Успешный запрос, возвращает данные валюты"),
            ApiResponse(responseCode = "404", description = "Валюта не найдена"),
            ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
        ]
    )
    fun getCurrency(@PathVariable id: UUID): ResponseEntity<Currency> {
        val currency = currencyService.getCurrencyById(id)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)

        return ResponseEntity.ok(currency)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные валюты")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Данные валюты успешно обновлены"),
            ApiResponse(responseCode = "400", description = "Некорректные данные в запросе"),
            ApiResponse(responseCode = "404", description = "Валюта не найдена"),
            ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
        ]
    )
    fun putCurrency(
        @PathVariable id: UUID,
        @Valid @RequestBody currencyRequest: CurrencyRequest
    ): ResponseEntity<Currency> {
        val updatedCurrency = currencyService.updateCurrency(id, currencyRequest.toCurrency())
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)

        return ResponseEntity.ok(updatedCurrency)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить валюту")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Валюта успешно удалена"),
            ApiResponse(responseCode = "404", description = "Валюта не найдена"),
            ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
        ]
    )
    fun deleteCurrency(@PathVariable id: UUID): ResponseEntity<Void> =
        if (currencyService.deleteCurrency(id))
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
}