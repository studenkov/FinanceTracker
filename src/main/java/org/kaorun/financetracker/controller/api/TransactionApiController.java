package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.kaorun.financetracker.model.TransactionModel;
import org.kaorun.financetracker.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Транзакции")
public class TransactionApiController {
    private final TransactionService transactionService;

    public TransactionApiController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    @Operation(summary = "Получить все транзакции")
    public List<TransactionModel> getAll() {
        return transactionService.findAll();
    }

    @PostMapping
    @Operation(summary = "Добавить новую транзакцию")
    @ApiResponse(responseCode = "200", description = "Успешно создано")
    public ResponseEntity<TransactionModel> create(@Valid @RequestBody TransactionModel transaction) {
        transactionService.add(transaction);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить транзакцию по ID")
    @ApiResponse(responseCode = "200", description = "Успешно обновлено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TransactionModel transaction) {
        if (transactionService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        transaction.setId(id);
        transactionService.add(transaction);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить транзакцию по ID")
    @ApiResponse(responseCode = "200", description = "Успешно удалено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (transactionService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        transactionService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Транзакция успешно удалена"));
    }
}