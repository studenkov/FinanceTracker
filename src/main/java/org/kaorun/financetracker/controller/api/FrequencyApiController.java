package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.kaorun.financetracker.model.FrequencyModel;
import org.kaorun.financetracker.service.FrequencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/frequencies")
@Tag(name = "Частоты повторений")
public class FrequencyApiController {

    private final FrequencyService frequencyService;

    public FrequencyApiController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

    @GetMapping
    @Operation(summary = "Получить все частоты повторений")
    public List<FrequencyModel> getAll() {
        return frequencyService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать новую частоту")
    @ApiResponse(responseCode = "200", description = "Успешно создано")
    public ResponseEntity<FrequencyModel> create(@Valid @RequestBody FrequencyModel frequency) {
        frequencyService.add(frequency);
        return ResponseEntity.ok(frequency);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить частоту по ID")
    @ApiResponse(responseCode = "200", description = "Успешно обновлено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody FrequencyModel frequency) {
        if (frequencyService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        frequency.setId(id);
        frequencyService.add(frequency);
        return ResponseEntity.ok(frequency);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить частоту по ID")
    @ApiResponse(responseCode = "200", description = "Успешно удалено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (frequencyService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        frequencyService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Частота успешно удалена"));
    }
}