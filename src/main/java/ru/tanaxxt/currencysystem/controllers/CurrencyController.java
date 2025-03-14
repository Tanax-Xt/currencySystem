package ru.tanaxxt.currencysystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/currencies")
@Tag(name = "Currencies")
public class CurrencyController {
    @GetMapping("")
    @Operation(summary = "Получить список отслеживаемых валют")
    public ResponseEntity<String> get_currencies() {
        return new ResponseEntity<>("No business logic", HttpStatus.OK);
    }


    @PostMapping("")
    @Operation(summary = "Добавить новую валюту для отслеживания")
    public ResponseEntity<String> post_currency() {
        return new ResponseEntity<>("No business logic", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию о конкретной валюте")
    public ResponseEntity<String> get_currency(@PathVariable String id) {
        return new ResponseEntity<>("No business logic", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные валюты")
    public ResponseEntity<String> put_currency(@PathVariable String id) {
        return new ResponseEntity<>("No business logic", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить валюту из отслеживания")
    public ResponseEntity<String> delete_currency(@PathVariable String id) {
        return new ResponseEntity<>("No business logic", HttpStatus.NO_CONTENT);
    }
}
