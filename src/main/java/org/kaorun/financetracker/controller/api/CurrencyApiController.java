package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.kaorun.financetracker.model.CurrencyModel;
import org.kaorun.financetracker.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/currencies")
@Tag(name = "Валюты")
public class CurrencyApiController {

    private final CurrencyService currencyService;

    public CurrencyApiController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    @Operation(summary = "Получить все валюты")
    public List<CurrencyModel> getAll() {
        return currencyService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать новую валюту")
    @ApiResponse(responseCode = "200", description = "Успешно создано")
    public ResponseEntity<CurrencyModel> create(@Valid @RequestBody CurrencyModel currency) {
        currencyService.add(currency);
        return ResponseEntity.ok(currency);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить валюту по ID")
    @ApiResponse(responseCode = "200", description = "Успешно обновлено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CurrencyModel currency) {
        if (currencyService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        currency.setId(id);
        currencyService.add(currency);
        return ResponseEntity.ok(currency);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить валюту по ID")
    @ApiResponse(responseCode = "200", description = "Успешно удалено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (currencyService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        currencyService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Валюта успешно удалена"));
    }
}