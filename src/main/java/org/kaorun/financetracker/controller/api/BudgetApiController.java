package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.kaorun.financetracker.model.BudgetModel;
import org.kaorun.financetracker.service.BudgetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budgets")
@Tag(name = "Бюджеты")
public class BudgetApiController {
    private final BudgetService budgetService;

    public BudgetApiController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    @Operation(summary = "Получить все бюджеты")
    public List<BudgetModel> getAll() {
        return budgetService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать новый бюджет")
    @ApiResponse(responseCode = "200", description = "Успешно создано")
    public ResponseEntity<BudgetModel> create(@Valid @RequestBody BudgetModel budget) {
        budgetService.add(budget);
        return ResponseEntity.ok(budget);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить бюджет по ID")
    @ApiResponse(responseCode = "200", description = "Успешно обновлено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BudgetModel budget) {
        if (budgetService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        budget.setId(id);
        budgetService.add(budget);
        return ResponseEntity.ok(budget);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить бюджет по ID")
    @ApiResponse(responseCode = "200", description = "Успешно удалено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (budgetService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        budgetService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Бюджет успешно удален"));
    }
}