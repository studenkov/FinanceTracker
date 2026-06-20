package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.kaorun.financetracker.model.TypeModel;
import org.kaorun.financetracker.service.TypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/types")
@Tag(name = "Типы операций")
public class TypeApiController {

    private final TypeService typeService;

    public TypeApiController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    @Operation(summary = "Получить все типы операций")
    public List<TypeModel> getAll() {
        return typeService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать новый тип операции")
    @ApiResponse(responseCode = "200", description = "Успешно создано")
    public ResponseEntity<TypeModel> create(@Valid @RequestBody TypeModel type) {
        typeService.add(type);
        return ResponseEntity.ok(type);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить тип операции по ID")
    @ApiResponse(responseCode = "200", description = "Успешно обновлено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TypeModel type) {
        if (typeService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        type.setId(id);
        typeService.add(type);
        return ResponseEntity.ok(type);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тип операции по ID")
    @ApiResponse(responseCode = "200", description = "Успешно удалено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (typeService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        typeService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Тип операции успешно удален"));
    }
}