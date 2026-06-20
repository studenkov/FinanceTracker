package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.kaorun.financetracker.model.GoalModel;
import org.kaorun.financetracker.service.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goals")
@Tag(name = "Цели")
public class GoalApiController {

    private final GoalService goalService;

    public GoalApiController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    @Operation(summary = "Получить все цели")
    public List<GoalModel> getAll() {
        return goalService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать новую цель")
    @ApiResponse(responseCode = "200", description = "Успешно создано")
    public ResponseEntity<GoalModel> create(@Valid @RequestBody GoalModel goal) {
        goalService.add(goal);
        return ResponseEntity.ok(goal);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить цель по ID")
    @ApiResponse(responseCode = "200", description = "Успешно обновлено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody GoalModel goal) {
        if (goalService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        goal.setId(id);
        goalService.add(goal);
        return ResponseEntity.ok(goal);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить цель по ID")
    @ApiResponse(responseCode = "200", description = "Успешно удалено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (goalService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        goalService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Цель успешно удалена"));
    }
}